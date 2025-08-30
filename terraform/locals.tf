locals {
  api_ingress_path_without_slash = replace(var.api_ingress_path, "/", "")
}