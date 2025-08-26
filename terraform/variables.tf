# Commn
  variable "subscription_id" {
    type        = string
    description = "Azure Subscription ID"
    # Default (Via tfvars)
  }

  variable "resource_group_name" {
    type    = string
    default = "tc3"
    description = "Nome do resource group"
  }

# foodcore-infra
  variable "foodcore-infra-rs-resource-group" {
    type        = string
    description = "Nome do resource group do foodcore-infra"
  }

  variable "foodcore-infra-rs-storage-account" {
    type        = string
    description = "Nome da conta de armazenamento do foodcore-infra"
  }

  variable "foodcore-infra-rs-container" {
    type        = string
    description = "Nome do contêiner do foodcore-infra"
  }

  variable "foodcore-infra-rs-key" {
    type        = string
    description = "Chave do foodcore-infra remote state"
  }