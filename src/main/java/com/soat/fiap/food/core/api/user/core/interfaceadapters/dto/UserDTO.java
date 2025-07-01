package com.soat.fiap.food.core.api.user.core.interfaceadapters.dto;

/**
 * DTO que representa os dados de um usuário na comunicação entre camadas.
 */
public record UserDTO(Long id, String username, String email, String document, Long roleId, boolean guest,
		boolean active) {
}
