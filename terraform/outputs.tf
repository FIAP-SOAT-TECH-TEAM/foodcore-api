output "acr_login_server_from_remote" {
  value = data.terraform_remote_state.acr.outputs.acr_login_server
}