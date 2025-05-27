package com.soat.fiap.food.core.api.user.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para respostas de roles
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "RoleResponse", description = "DTO para resposta com informações de roles")
public class RoleResponse {

    @Schema(description = "Identificador único da role", example = "1")
    private Long id;

    @Schema(description = "Nome da role", example = "Admin")
    private String name;

    @Schema(description = "Descrição da função da role", example = "Administra tudo")
    private String description;

    @Schema(description = "Data e hora de criação do registro", example = "2023-01-01T10:00:00")
    private LocalDateTime createdAt;

    @Schema(description = "Data e hora da última atualização", example = "2023-01-10T11:30:00")
    private LocalDateTime updatedAt;


}