package com.soat.fiap.food.core.api.user.core.application.inputs;

/**
 * Representa um DTO de entrada da aplicação (Application Layer), contendo apenas os dados necessários
 * para criação ou atualização de um usuário.
 *
 * @param guest    Indica se o usuário é do tipo guest.
 * @param name     Nome completo do usuário.
 * @param username Apelido do usuário.
 * @param email    Endereço de e-mail do usuário.
 * @param password Senha do usuário.
 * @param document Documento do usuário (CPF/CNPJ).
 */
public record UserInput(
        boolean guest,
        String name,
        String username,
        String email,
        String password,
        String document
) {
}