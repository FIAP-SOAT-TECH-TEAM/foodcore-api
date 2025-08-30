module "helm" {
  source = "./modules/helm"

  foodcore-infra-rs-container = var.foodcore-infra-rs-container
  foodcore-infra-rs-key = var.foodcore-infra-rs-key
  foodcore-infra-rs-resource-group = var.foodcore-infra-rs-resource-group
  foodcore-infra-rs-storage-account = var.foodcore-infra-rs-storage-account
  release_name          = var.release_name
  repository_url        = "https://${data.azurerm_container_registry.acr.login_server}/helm/foodcoreapi"
  chart_name            = var.chart_name
  chart_version         = var.chart_version
  release_namespace     = var.release_namespace
  docker_image_uri      = var.docker_image_uri
  docker_image_tag      = var.docker_image_tag
  jwt_secret            = var.jwt_secret
  mercadopago_base_url  = var.mercadopago_base_url
  mercadopago_token     = var.mercadopago_token
  mercadopago_user_id   = var.mercadopago_user_id
  mercadopago_pos_id    = var.mercadopago_pos_id
  api_ingress_path      = var.api_ingress_path
}

module "apim" {
  source = "./modules/apim"

  foodcore-infra-rs-container = var.foodcore-infra-rs-container
  foodcore-infra-rs-key = var.foodcore-infra-rs-key
  foodcore-infra-rs-resource-group = var.foodcore-infra-rs-resource-group
  foodcore-infra-rs-storage-account = var.foodcore-infra-rs-storage-account
  apim_api_name        = var.apim_api_name
  apim_api_version     = var.apim_api_version
  apim_display_name    = var.apim_display_name
  swagger_path         = var.swagger_path
  api_ingress_path     = var.api_ingress_path

  depends_on = [module.helm]
}