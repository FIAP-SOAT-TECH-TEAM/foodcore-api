resource "azurerm_api_management_api" "foodcoreapi_apim" {
  name                = var.apim_api_name
  resource_group_name = data.terraform_remote_state.infra.outputs.apim_resource_group
  api_management_name = data.terraform_remote_state.infra.outputs.apim_name
  revision            = var.apim_api_version
  display_name        = var.apim_display_name
  path                = var.api_ingress_path
  protocols           = ["https"]

  import {
    content_format = "openapi+json"
    content_value  = file(var.swagger_path)
  }
}

resource "azurerm_api_management_api_policy" "set_backend_api" {
  api_name            = azurerm_api_management_api.foodcoreapi_apim.name
  api_management_name = data.terraform_remote_state.infra.outputs.apim_name
  resource_group_name = data.terraform_remote_state.infra.outputs.apim_resource_group

  xml_content = <<XML
  <policies>
    <inbound>
      <base /> <!-- Herda as policies globais configuradas no API Management -->

      <!-- Extrai token -->
      <set-variable name="bearerToken" value="@(context.Request.Headers.GetValueOrDefault("Authorization", "").Split(' ').Last())" />

      <!-- Normaliza Path (adiciona / se não existir) -->
      <set-variable name="normalizedPath" value="@{
          var path = context.Request?.Url?.Path ?? "";
          var normalizedPath = path.StartsWith("/") ? path : $"/{path}";
          return normalizedPath;
      }" />

      <!-- Valida token -->
      <send-request mode="new" response-variable-name="authResponse" timeout="10">
        <set-url>@($"${data.terraform_remote_state.azfunc.outputs.auth_api_validate_endpoint}?access_token={context.Variables["bearerToken"]}&url={context.Variables["normalizedPath"]}&http_method={context.Operation.Method}")</set-url>
        <set-method>GET</set-method>
      </send-request>

      <!-- Retorna authResponse se não for 200 -->
      <choose>
        <when condition="@(context.Variables.GetValueOrDefault<IResponse>("authResponse")?.StatusCode != 200)">
          <return-response response-variable-name="authResponse" />
        </when>
      </choose>

      <!-- Converte body da auth API para JObject -->
      <set-variable name="authBody" value="@(((IResponse)context.Variables["authResponse"]).Body.As<JObject>(true))" />

      <!-- Constrói o objeto de headers para enviar ao backend -->
      <set-variable name="headersToSet" value="@{
        var authBody = context.Variables.GetValueOrDefault<JObject>("authBody");
        var headers = new JObject();
        if (authBody != null)
        {
            headers["Auth-Subject"]   = authBody["subject"]?.ToString();
            headers["Auth-Name"]      = authBody["name"]?.ToString();
            headers["Auth-Email"]     = authBody["email"]?.ToString();
            headers["Auth-Cpf"]       = authBody["cpf"]?.ToString();
            headers["Auth-Role"]      = authBody["role"]?.ToString();
            headers["Auth-CreatedAt"] = authBody["createdAt"]?.ToString();
        }
        return headers;
      }" />

      <!-- Aplica headers ao backend -->
      @{
        var headers = context.Variables.GetValueOrDefault<JObject>("headersToSet");
        if (headers != null)
        {
            foreach (var prop in headers.Properties())
            {
                context.Request.Headers.Set(prop.Name, prop.Value?.ToString().XmlEscape());
            }
        }
      }

      <!-- Define o backend real -->
      <set-backend-service base-url="http://${data.terraform_remote_state.infra.outputs.api_private_dns_fqdn}/${var.api_ingress_path}" />
    </inbound>

    <backend>
      <base /> <!-- Herda policies globais aplicadas ao backend -->
    </backend>

    <outbound>
      <base /> <!-- Herda policies globais aplicadas à resposta antes de retornar ao cliente -->
    </outbound>

    <on-error>
      <!-- Normaliza Path (adiciona / se não existir) -->
      <set-variable name="normalizedPath" value="@{
          var path = context.Request?.Url?.Path ?? "";
          var normalizedPath = path.StartsWith("/") ? path : $"/{path}";
          return normalizedPath;
      }" />
      <choose>
        <when condition="@(context.LastError != null)">
          <return-response>
            <set-status code="@(context.Response?.StatusCode ?? 500)" reason="Other errors" />
            <set-header name="Content-Type" exists-action="override">
              <value>application/json</value>
            </set-header>
            <set-body>@{
                var error = new JObject();
                error["timestamp"] = DateTime.UtcNow.ToString("o"); // ISO 8601
                error["status"]    = context.Response?.StatusCode ?? 500;
                error["message"]   = context.LastError.Message;
                error["path"]      = context.Variables.GetValueOrDefault<string>("normalizedPath");
                return error.ToString(Newtonsoft.Json.Formatting.Indented);
            }</set-body>
          </return-response>
        </when>
      </choose>
    </on-error>

  </policies>
  XML
}

resource "azurerm_api_management_product_api" "foodcoreapi_start_product_assoc" {
  api_name            = azurerm_api_management_api.foodcoreapi_apim.name
  product_id          = data.terraform_remote_state.infra.outputs.apim_foodcore_start_productid
  api_management_name = data.terraform_remote_state.infra.outputs.apim_name
  resource_group_name = data.terraform_remote_state.infra.outputs.apim_resource_group
}