resource "helm_release" "foodcoreapi" {
  name            = var.release_name
  repository      = var.repository_url
  chart           = var.chart_name
  version         = var.chart_version
  namespace       = var.release_namespace
  upgrade_install = true

  set {
    name  = "api.image.repository"
    value = var.docker_image_uri
  }

  set {
    name  = "api.image.tag"
    value = var.docker_image_tag
  }

  set {
    name  = "ingress.hosts[0].host"
    value = data.terraform_remote_state.infra.outputs.api_private_dns_fqdn
  }

  set {
    name  = "api.ingress.path"
    value = var.api_ingress_path
  }

  set {
    name  = "api.azure.blob.connectionString"
    value = data.terraform_remote_state.infra.outputs.storage_account_connection_string
  }

  set {
    name  = "api.azure.blob.containerName"
    value = data.terraform_remote_state.infra.outputs.storage_container_name
  }

  set {
    name  = "api.auth.jwt.secret"
    value = var.jwt_secret
  }

  set {
    name  = "ingress-nginx.controller.service.annotations.service\\.beta\\.kubernetes\\.io/azure-load-balancer-ipv4"
    value = data.terraform_remote_state.infra.outputs.aks_subnet_last_usable_ip
  }

  set {
    name  = "api.mercadoPago.baseUrl"
    value = var.mercadopago_base_url
  }

  set {
    name  = "api.mercadoPago.token"
    value = var.mercadopago_token
  }

  set {
    name  = "api.mercadoPago.userId"
    value = var.mercadopago_user_id
  }

  set {
    name  = "api.mercadoPago.posId"
    value = var.mercadopago_pos_id
  }

  set {
    name  = "api.mercadoPago.notificationUrl"
    value = "${data.terraform_remote_state.infra.outputs.apim_gateway_url}/payments/webhook"
  }

}