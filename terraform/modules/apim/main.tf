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
      <base />
      
      <!-- 1. Se não houver Authorization header -->
      <choose>
        <when condition="@(context.Request.Headers.GetValueOrDefault("Authorization","") == "")">
          <return-response>
            <set-status code="401" reason="Unauthorized" />
          </return-response>
        </when>
      </choose>

      <!-- 2. Extrair o token do header -->
      <set-variable name="bearerToken" value="@((string)context.Request.Headers.GetValueOrDefault("Authorization","").Substring(7))" />

      <!-- 3. Chamar serviço de validação com token na query string -->
      <send-request mode="new" response-variable-name="authResponse" timeout="10" ignore-error="false">
        <set-url>@($"${data.terraform_remote_state.azfunc.outputs.auth_api_validate_endpoint}?access_token={context.Variables["bearerToken"]}")</set-url>
        <set-method>GET</set-method>
      </send-request>

      <!-- 4. Se não for 200, retorna resposta da function de auth -->
      <choose>
        <when condition="@(context.Variables["authResponse"]?.StatusCode != 200)">
          <return-response response-variable-name="authResponse" />
        </when>
      </choose>

      <!-- 5. Parse body do authResponse -->
      <set-variable name="authBody" value="@((IResponse)context.Variables["authResponse"]).Body.As<JObject>()" />

      <!-- 6. Inserir os headers no request para o backend -->
      <set-header name="Auth-IdToken" exists-action="override">
        <value>@((string)((JObject)context.Variables["authBody"])["idToken"])</value>
      </set-header>
      <set-header name="Auth-AccessToken" exists-action="override">
        <value>@((string)((JObject)context.Variables["authBody"])["accessToken"])</value>
      </set-header>
      <set-header name="Auth-RefreshToken" exists-action="override">
        <value>@((string)((JObject)context.Variables["authBody"])["refreshToken"])</value>
      </set-header>
      <set-header name="Auth-ExpiresIn" exists-action="override">
        <value>@((int)((JObject)context.Variables["authBody"])["expiresIn"])</value>
      </set-header>
      <set-header name="Auth-TokenType" exists-action="override">
        <value>@((string)((JObject)context.Variables["authBody"])["tokenType"])</value>
      </set-header>

      <!-- 7. Encaminhar para o backend final -->
      <set-backend-service base-url="http://${data.terraform_remote_state.infra.outputs.api_private_dns_fqdn}/${var.api_ingress_path}" />

    </inbound>
    <backend>
      <base />
    </backend>
    <outbound>
      <base />
    </outbound>
  </policies>
XML
}

resource "azurerm_api_management_product_api" "foodcoreapi_start_product_assoc" {
  api_name            = azurerm_api_management_api.foodcoreapi_apim.name
  product_id          = data.terraform_remote_state.infra.outputs.apim_foodcore_start_productid
  api_management_name = data.terraform_remote_state.infra.outputs.apim_name
  resource_group_name = data.terraform_remote_state.infra.outputs.apim_resource_group
}