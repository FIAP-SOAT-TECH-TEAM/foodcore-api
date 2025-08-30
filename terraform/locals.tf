locals {
  acr_name_only = replace(data.azurerm_container_registry.acr.login_server, ".azurecr.io", "")
}