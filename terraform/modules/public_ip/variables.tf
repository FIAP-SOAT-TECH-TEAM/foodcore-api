variable "resource_group_name" {
  type = string
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