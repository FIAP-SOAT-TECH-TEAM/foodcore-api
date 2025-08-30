data "terraform_remote_state" "infra" {
  backend = "azurerm"
  config = {
    resource_group_name  = var.foodcore-infra-rs-resource-group
    storage_account_name = var.foodcore-infra-rs-storage-account
    container_name       = var.foodcore-infra-rs-container
    key                  = var.foodcore-infra-rs-key
  }
}

data "azurerm_kubernetes_cluster" "aks" {
  name                = data.terraform_remote_state.infra.outputs.aks_name
  resource_group_name = data.terraform_remote_state.infra.outputs.aks_resource_group
}

data "azurerm_container_registry" "acr" {
  name                = data.terraform_remote_state.infra.outputs.acr_name
  resource_group_name = data.terraform_remote_state.infra.outputs.acr_resource_group
}