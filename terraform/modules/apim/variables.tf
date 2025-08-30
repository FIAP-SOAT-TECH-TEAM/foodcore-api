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