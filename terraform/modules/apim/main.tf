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

          <!-- Cria uma variável booleana para verificar se o header Authorization existe e não está vazio -->
          <set-variable name="hasAuthHeader" value="@(context.Request.Headers.ContainsKey("Authorization") && !string.IsNullOrEmpty(context.Request.Headers.GetValueOrDefault("Authorization", "")))" />

          <!-- Se não existir Authorization header, retorna 401 Unauthorized -->
          <choose>
              <when condition="@(!((bool)context.Variables["hasAuthHeader"]))">
                  <return-response>
                      <set-status code="401" reason="Unauthorized" />
                      <set-header name="WWW-Authenticate" exists-action="override">
                          <value>Bearer error="invalid_token" error_description="Authorization header is missing or empty."</value>
                      </set-header>
                  </return-response>
              </when>
          </choose>

          <!-- Extrai apenas o token (parte após o "Bearer ") do header Authorization -->
          <set-variable name="bearerToken" value="@(context.Request.Headers.GetValueOrDefault("Authorization", "").Split(' ').Last())" />

          <!-- Chama um endpoint externo (auth API) para validar o token -->
          <send-request mode="new" response-variable-name="authResponse" timeout="10">
              <set-url>@($"${data.terraform_remote_state.azfunc.outputs.auth_api_validate_endpoint}?access_token={context.Variables}")</set-url>
              <set-method>GET</set-method>
          </send-request>

          <!-- Se a resposta da auth API não for 200, retorna diretamente essa resposta -->
          <choose>
              <when condition="@(context.Variables.GetValueOrDefault<IResponse>("authResponse")?.StatusCode != 200)">
                  <return-response response-variable-name="authResponse" />
              </when>
          </choose>
          
          <!-- Converte o corpo da resposta da auth API para JObject e armazena em authBody -->
          <set-variable name="authBody" value="@(((IResponse)context.Variables).Body.As<JObject>(true))" />

          <!-- Se authBody não for nulo, propaga os valores retornados no body como headers para o backend -->
          @{
              var authBody = context.Variables.GetValueOrDefault<JObject>("authBody");
              if (authBody != null) {
                  var headersToSet = new Dictionary<string, JToken>
                  {
                      { "Auth-IdToken", authBody },
                      { "Auth-AccessToken", authBody },
                      { "Auth-RefreshToken", authBody },
                      { "Auth-ExpiresIn", authBody["expiresIn"] },
                      { "Auth-TokenType", authBody },
                      { "Auth-CreatedAt", authBody["createdAt"] }
                  };

                  foreach (var header in headersToSet) {
                      <set-header name="@(header.Key)" exists-action="override">
                          <value>@(header.Value?.ToString())</value>
                      </set-header>
                  }
              }
          }

          <!-- Define o backend real para onde a requisição será roteada -->
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
          <!-- Se ocorreu erro, retorna resposta 500 com detalhes em JSON -->
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