data "terraform_remote_state" "infra" {
  backend = "azurerm"
  config = {
    resource_group_name  = var.foodcore-infra-rs-resource-group
    storage_account_name = var.foodcore-infra-rs-storage-account
    container_name       = var.foodcore-infra-rs-container
    key                  = var.foodcore-infra-rs-key
  }
}