package com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * DTO para resposta de obtenção do token do usuário
 */
@Data @AllArgsConstructor @Schema(name = "UserTokenResponse", description = "Resposta com dados do usuário e token JWT")
public class UserTokenResponse {

	@Schema(description = "Dados do usuário")
	private UserResponse user;

	@Schema(description = "Token de autenticação JWT", example = "eyJhbGciOiJIUzI1NiIsInR...")
	private String token;
}
