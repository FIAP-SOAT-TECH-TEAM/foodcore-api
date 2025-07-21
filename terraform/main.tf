module "resource_group" {
  source              = "./modules/resource_group"
  resource_group_name = var.resource_group_name
  location            = var.location
}

module "public_ip" {
  source              = "./modules/public_ip"
  resource_group_name = module.resource_group.name
  location            = var.location
  dns_label           = var.public_ip_dns_label
  allocation_method   = var.allocation_method
  sku                 = var.sku
}

module "aks" {
  source              = "./modules/aks"
  resource_group_name = module.resource_group.name
  location            = var.location
  dns_prefix          = var.dns_prefix
  node_count          = var.node_count
  vm_size             = var.vm_size
  identity_type       = var.identity_type
  kubernetes_version  = var.kubernetes_version
  resource_group_id   = module.resource_group.id
}