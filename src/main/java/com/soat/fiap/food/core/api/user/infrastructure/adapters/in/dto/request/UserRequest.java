package com.soat.fiap.food.core.api.user.infrastructure.adapters.in.dto.request;

import com.soat.fiap.food.core.api.shared.vo.RoleType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


/**
 * DTO para requisições de criação/atualização de usuários
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "UserRequest", description = "DTO para criação ou atualização de usuários")
public class UserRequest {

    @Schema(description = "Nome completo do usuário", example = "João Silva", required = false)
    private String name;

    @Schema(description = "Apelido do usuário", example = "Jão", required = false)
    private String username = null;

    @Email(message = "O email deve ser válido")
    @Schema(description = "Email do usuário", example = "joao.silva@email.com", required = false)
    private String email = null;

    @Schema(description = "Senha do usuário", example = "João Silva", required = false)
    private String password;

    @Schema(description = "Documento do usuário (apenas números ou formatado)", example = "123.456.789-00", required = false)
    private String document = null;

    @Builder.Default
    @Schema(description = "Tipo de role (ADMIN=1, USER=2)", example = "2")
    private RoleType role = RoleType.USER; //define a role user como default caso nenhuma seja informada
}
