variable "resource_group_name" {
  type    = string
  default = "tc2"
}

variable "location" {
  type    = string
  default = "East US"
}

variable "dns_prefix" {
  type    = string
  default = "foodcore"
}

variable "public_ip_dns_label" {
  type    = string
  default = "foodcoreapi"
}

# Via tfvars
variable "subscription_id" {
  type        = string
  description = "Azure Subscription ID"
}

variable "allocation_method" {
  type    = string
  default = "Static"
}

variable "sku" {
  type    = string
  default = "Standard"
}

variable "node_count" {
  type    = number
  default = 1
}

variable "vm_size" {
  type    = string
  default = "Standard_DS2_v2"
}

variable "identity_type" {
  type = string
  default = "SystemAssigned"
}

variable "kubernetes_version" {
  type    = string
  default = "1.32.5" 
}