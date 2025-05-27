package com.soat.fiap.food.core.api.user.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO para requisições de criação/atualização de roles
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "RoleRequest", description = "DTO para criação ou atualização de roles")
public class RoleRequest {

    @Schema(description = "Nome da role", example = "ADMIN", required = true)
    private String name;

    @Schema(description = "Descrição da função da role", example = "Administra tudo", required = true)
    private String description;

}
