package com.soat.fiap.food.core.api.user.infrastructure.adapters.in.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisições de Login de usuários
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "LoginRequest", description = "DTO para login de usuários")
public class LoginRequest {


    @Builder.Default
    @Email(message = "O email deve ser válido")
    @Schema(description = "Email do usuário", example = "joao.silva@email.com", required = true)
    private String email = null;

    @Schema(description = "Senha do usuário", example = "João Silva", required = true)
    private String password;
}
