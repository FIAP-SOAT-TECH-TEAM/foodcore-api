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