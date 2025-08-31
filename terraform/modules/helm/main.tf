resource "helm_release" "foodcoreapi" {
  name            = var.release_name
  repository      = var.repository_url
  chart           = var.chart_name
  version         = var.chart_version
  namespace       = var.release_namespace
  upgrade_install = true

  set = [
    {
      name  = "api.image.repository"
      value = var.docker_image_uri
    },
    {
      name  = "api.image.tag"
      value = var.docker_image_tag
    },
    {
      name  = "ingress.hosts[0].host"
      value = data.terraform_remote_state.infra.outputs.api_private_dns_fqdn
    },
    {
      name  = "api.ingress.path"
      value = var.api_ingress_path
    },
    {
      name  = "api.azure.blob.connectionString"
      value = data.terraform_remote_state.infra.outputs.storage_account_connection_string
    },
    {
      name  = "api.azure.blob.containerName"
      value = data.terraform_remote_state.infra.outputs.storage_container_name
    },
    {
      name  = "api.auth.jwt.secret"
      value = var.jwt_secret
    },
    {
      name  = "ingress-nginx.controller.service.loadBalancerIP"
      value = data.terraform_remote_state.infra.outputs.aks_subnet_last_usable_ip
    },
    {
      name  = "api.mercadoPago.baseUrl"
      value = var.mercadopago_base_url
    },
    {
      name  = "api.mercadoPago.token"
      value = var.mercadopago_token
    },
    {
      name  = "api.mercadoPago.userId"
      value = var.mercadopago_user_id
    },
    {
      name  = "api.mercadoPago.posId"
      value = var.mercadopago_pos_id
    },
    {
      name  = "api.mercadoPago.notificationUrl"
      value = "${data.terraform_remote_state.infra.outputs.apim_gateway_url}/payments/webhook"
    }
  ]
  
}