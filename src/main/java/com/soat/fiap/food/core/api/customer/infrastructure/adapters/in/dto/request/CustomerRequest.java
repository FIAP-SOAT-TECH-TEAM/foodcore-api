package com.soat.fiap.food.core.api.customer.infrastructure.adapters.in.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisições de criação/atualização de clientes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CustomerRequest", description = "DTO para criação ou atualização de clientes")
public class CustomerRequest {
    
    @NotBlank(message = "O nome do cliente é obrigatório")
    @Schema(description = "Nome completo do cliente", example = "João Silva", required = true)
    private String name;
    
    @NotBlank(message = "O email do cliente é obrigatório")
    @Email(message = "O email deve ser válido")
    @Schema(description = "Email do cliente", example = "joao.silva@email.com", required = true)
    private String email;
    
    @NotBlank(message = "O DOCUMENT do cliente é obrigatório")
    @Schema(description = "DOCUMENT do cliente (apenas números ou formatado)", example = "123.456.789-00", required = true)
    private String document;
    
    @Schema(description = "Telefone do cliente", example = "(11) 98765-4321")
    private String phone;
}