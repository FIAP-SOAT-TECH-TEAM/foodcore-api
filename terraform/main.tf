module "helm" {
  source = "./modules/helm"

  release_name          = var.release_name
  repository_url        = "https://${data.azurerm_container_registry.acr.login_server}.azurecr.io/helm/foodcoreapi"
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

  apim_api_name        = var.apim_api_name
  apim_api_version     = var.apim_api_version
  apim_display_name    = var.apim_display_name
  swagger_path         = var.swagger_path
  api_ingress_path     = var.api_ingress_path

  depends_on = [module.helm]
}