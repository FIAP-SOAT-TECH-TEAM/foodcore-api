variable "release_name" {
  type        = string
  description = "Nome do release do Helm."
}

variable "repository_url" {
  type        = string
  description = "URL do repositório Helm onde o chart está hospedado."
}

variable "chart_name" {
  type        = string
  description = "Nome do chart Helm a ser instalado."
}

variable "chart_version" {
  type        = string
  description = "Versão do chart Helm."
}

variable "release_namespace" {
  type        = string
  description = "Namespace Kubernetes onde o release será instalado."
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