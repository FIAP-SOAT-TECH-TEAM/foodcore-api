terraform {
  backend "azurerm" {
    resource_group_name  = "tc1"
    storage_account_name = "fastfoodcoreapi"
    container_name       = "tfstate"
    key                  = "aks-foodcoreapi.terraform.tfstate"
  }
}