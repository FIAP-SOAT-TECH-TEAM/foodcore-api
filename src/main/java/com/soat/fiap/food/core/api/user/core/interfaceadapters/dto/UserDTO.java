package com.soat.fiap.food.core.api.user.core.interfaceadapters.dto;

import java.time.LocalDateTime;

/**
 * DTO que representa os dados de um usuário na comunicação entre camadas.
 */
public record UserDTO(Long id, String name, String username, String email, String password, String document,
		Long roleId, boolean guest, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
