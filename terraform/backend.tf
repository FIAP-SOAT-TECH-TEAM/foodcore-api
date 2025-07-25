terraform {
  backend "azurerm" {
    resource_group_name  = "terraform-backend"
    storage_account_name = "fastfoodcoreapitfstate"
    container_name       = "tfstate"
    key                  = "aks-foodcoreapi.terraform.tfstate"
  }
}
