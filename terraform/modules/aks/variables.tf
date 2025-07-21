variable "resource_group_name" {
  type = string
  
  validation {
    condition = can(regex("^[a-zA-Z0-9]+$", var.resource_group_name))
    error_message = "O nome do resource group deve conter apenas letras e números."
  }
}

variable "resource_group_id" {
  type        = string
  description = "ID do resource group onde está o IP fixo"
}

variable "location" {
  type = string
}

variable "dns_prefix" {
  type = string

  validation {
    condition     = length(var.dns_prefix) >= 1 && length(var.dns_prefix) <= 54
    error_message = "O 'dns_prefix' deve ter entre 1 e 54 caracteres."
  }
}

variable "node_count" {
  type    = number
  default = 1

  validation {
    condition     = var.node_count >= 1 && var.node_count <= 5
    error_message = "O 'node_count' deve ser um número entre 1 e 5."
  }
}

variable "vm_size" {
  type    = string
}

variable "identity_type" {
  type = string
}

variable "kubernetes_version" {
  type    = string
}