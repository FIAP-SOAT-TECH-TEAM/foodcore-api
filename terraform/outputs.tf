output "resource_group_name" {
  value = module.resource_group.name
}

output "aks_name" {
  value = module.aks.aks_name
}

output "public_ip_fqdn" {
  value = module.public_ip.fqdn
}

output "public_ip_address" {
  value = module.public_ip.ip_address
}