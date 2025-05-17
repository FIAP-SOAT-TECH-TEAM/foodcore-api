package com.soat.fiap.food.core.api.user.infrastructure.adapters.in.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
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
@Schema(name = "CustomerRequest", description = "DTO para criação ou atualização de usuários")
public class UserRequest {
    
    @NotBlank(message = "O nome do usuário é obrigatório")
    @Schema(description = "Nome completo do usuário", example = "João Silva", required = true)
    private String name;
    
    @NotBlank(message = "O email do usuário é obrigatório")
    @Email(message = "O email deve ser válido")
    @Schema(description = "Email do usuário", example = "joao.silva@email.com", required = true)
    private String email;
    
    @NotBlank(message = "O DOCUMENT do usuário é obrigatório")
    @Schema(description = "DOCUMENT do usuário (apenas números ou formatado)", example = "123.456.789-00", required = true)
    private String document;
    
    @Schema(description = "Telefone do usuário", example = "(11) 98765-4321")
    private String phone;
}