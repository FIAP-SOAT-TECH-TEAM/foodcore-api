resource "azurerm_api_management_api" "foodcoreapi_apim" {
  name                = var.apim_api_name
  resource_group_name = data.terraform_remote_state.infra.outputs.apim_resource_group
  api_management_name = data.terraform_remote_state.infra.outputs.apim_name
  revision            = var.apim_api_version
  display_name        = var.apim_display_name
  path                = var.api_ingress_path
  protocols           = ["http"]

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
      <!-- Define backend global -->
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