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

      <!-- Verifica se Authorization header existe -->
      <set-variable name="hasAuthHeader" value="@(context.Request.Headers.ContainsKey("Authorization") && !string.IsNullOrEmpty(context.Request.Headers.GetValueOrDefault("Authorization", "")))" />

      <!-- Retorna 401 se não existir -->
      <choose>
        <when condition="@(!((bool)context.Variables["hasAuthHeader"]))">
          <return-response>
            <set-status code="401" reason="Unauthorized" />
            <set-header name="Content-Type" exists-action="override">
              <value>application/json</value>
            </set-header>
            <set-body>@{
              var error = new JObject();
              error["reason"] = "invalid_token";
              error["message"] = "Authorization header não informado ou vazio.";
              return error.ToString(Newtonsoft.Json.Formatting.Indented);
            }</set-body>
          </return-response>
        </when>
      </choose>

      <!-- Extrai token -->
      <set-variable name="bearerToken" value="@(context.Request.Headers.GetValueOrDefault("Authorization", "").Split(' ').Last())" />

      <!-- Valida token -->
      <send-request mode="new" response-variable-name="authResponse" timeout="10">
        <set-url>@($"${data.terraform_remote_state.azfunc.outputs.auth_api_validate_endpoint}?access_token={context.Variables["bearerToken"]}")</set-url>
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

      <!-- Constrói a lista de headers para enviar ao backend -->
      <set-variable name="headersToSet" value="@{
        var authBody = context.Variables.GetValueOrDefault<JObject>("authBody");
        var headers = new JArray();
        if (authBody != null)
        {
            headers.Add(new JObject { ["key"] = "Auth-Subject",      ["value"] = authBody["subject"] });
            headers.Add(new JObject { ["key"] = "Auth-Name",         ["value"] = authBody["name"] });
            headers.Add(new JObject { ["key"] = "Auth-Email",        ["value"] = authBody["email"] });
            headers.Add(new JObject { ["key"] = "Auth-Cpf",          ["value"] = authBody["cpf"] });
            headers.Add(new JObject { ["key"] = "Auth-Role",         ["value"] = authBody["role"] });
            headers.Add(new JObject { ["key"] = "Auth-CreatedAt",    ["value"] = authBody["createdAt"] });
        }
        return headers;
      }" />

      <!-- Aplica headers ao backend -->
      @{ 
        var headers = context.Variables.GetValueOrDefault<JArray>("headersToSet");
        if (headers != null && headers.Count > 0)
        {
            foreach (var header in headers)
            {
              context.Request.Headers.Set(header["key"]?.ToString(), header["value"]?.ToString()?.XmlEscape());
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
      <base /> <!-- Herda tratamento de erros globais -->
      <choose>
        <when condition="@(context.LastError != null)">
          <return-response>
            <set-status code="500" reason="Internal Server Error" />
            <set-header name="Content-Type" exists-action="override">
              <value>application/json</value>
            </set-header>
            <set-body>@{
              var error = new JObject();
              error["source"] = (string)context.LastError.Source;
              error["reason"] = (string)context.LastError.Reason;
              error["message"] = (string)context.LastError.Message;
              error["policyId"] = (string)context.LastError.PolicyId;
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