resource "azurerm_public_ip" "ip" {
  name                = "${var.dns_label}-public-ip"
  location            = var.location
  resource_group_name = var.resource_group_name
  allocation_method   = var.allocation_method
  sku                 = var.sku
  domain_name_label   = var.dns_label
}