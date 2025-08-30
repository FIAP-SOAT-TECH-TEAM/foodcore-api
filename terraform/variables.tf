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

# HELM

  variable "release_name" {
    type        = string
    description = "Nome do release do Helm."
    default = "foodcoreapi"
  }

  variable "release_namespace" {
    type        = string
    description = "Namespace Kubernetes onde o release será instalado."
    default = "default"
  }

  variable "chart_name" {
    type        = string
    description = "Nome do chart Helm a ser instalado."
  }

  variable "chart_version" {
    type        = string
    description = "Versão do chart Helm."
  }

  variable "docker_image_uri" {
    type        = string
    description = "URI da imagem Docker a ser utilizada."
  }

  variable "docker_image_tag" {
    type        = string
    description = "Tag da imagem Docker a ser utilizada."
  }

  variable "jwt_secret" {
    type        = string
    description = "Segredo para assinatura de tokens JWT."
  }

  variable "mercadopago_base_url" {
    type        = string
    description = "URL base da API do MercadoPago."
  }

  variable "mercadopago_token" {
    type        = string
    description = "Token da API do MercadoPago."
  }

  variable "mercadopago_user_id" {
    type        = string
    description = "User ID da API do MercadoPago."
  }

  variable "mercadopago_pos_id" {
    type        = string
    description = "POS ID da API do MercadoPago."
  }

  variable "api_ingress_path" {
    type        = string
    description = "Caminho de ingress da API."
  }

  variable "repository_url" {
    type        = string
    description = "URL do repositório Helm onde o chart está hospedado."
    default     = "oci://${data.azurerm_container_registry.acr.login_server}/helm"
  }

# APIM
  variable "apim_api_name" {
    description = "Nome da API no API Management"
    type        = string
    default = "foodcore-api"
  }

  variable "apim_api_path" {
    description = "Caminho da API no API Management"
    type        = string
    default     = "/"
  }

  variable "apim_api_version" {
    description = "Versão da API no API Management"
    type        = string
    default     = "1"
  }

  variable "apim_display_name" {
    description = "Nome exibido da API no API Management"
    type        = string
    default     = "Foodcore API"
  }

  variable "swagger_path" {
    description = "Caminho do arquivo swagger.json"
    type        = string
  }
