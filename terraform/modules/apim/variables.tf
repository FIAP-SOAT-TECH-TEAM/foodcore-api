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

variable "apim_api_name" {
  description = "Nome da API no API Management"
  type        = string
}

variable "apim_api_version" {
  description = "Versão da API no API Management"
  type        = string
}

variable "apim_display_name" {
  description = "Nome exibido da API no API Management"
  type        = string
}

variable "swagger_path" {
  description = "Caminho do arquivo swagger.json"
  type        = string
}

variable "api_ingress_path" {
  type        = string
  description = "Caminho de ingress da API."
}