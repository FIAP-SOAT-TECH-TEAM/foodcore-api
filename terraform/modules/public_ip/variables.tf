variable "resource_group_name" {
  type = string
  
  validation {
    condition = can(regex("^[a-zA-Z0-9]+$", var.resource_group_name))
    error_message = "O nome do resource group deve conter apenas letras e números."
  }
}

variable "location" {
  type = string
}

variable "dns_label" {
  type = string

  validation {
    condition     = length(var.dns_label) >= 1 && length(var.dns_label) <= 54
    error_message = "O 'dns_label' deve ter entre 1 e 54 caracteres."
  }
}

variable "allocation_method" {
  type = string
}

variable "sku" {
  type = string
}