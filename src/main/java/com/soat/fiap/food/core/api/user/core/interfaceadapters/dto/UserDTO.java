package com.soat.fiap.food.core.api.user.core.interfaceadapters.dto;

import java.time.LocalDateTime;

/**
 * DTO utilizado para representar dados da entidade User. Serve como objeto de
 * transferência entre o domínio e o mundo externo (DataSource).
 */
public record UserDTO(Long id, String name, String username, String email, String password, String document,
		Long roleId, boolean guest, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
