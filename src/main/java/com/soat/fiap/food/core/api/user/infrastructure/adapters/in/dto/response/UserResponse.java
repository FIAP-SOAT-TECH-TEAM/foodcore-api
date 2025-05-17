package com.soat.fiap.food.core.api.user.infrastructure.adapters.in.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * DTO para respostas de clientes
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CustomerResponse", description = "DTO para resposta com informações de clientes")
public class UserResponse {
    
    @Schema(description = "Identificador único do cliente", example = "1")
    private Long id;
    
    @Schema(description = "Nome completo do cliente", example = "João Silva")
    private String name;
    
    @Schema(description = "Email do cliente", example = "joao.silva@email.com")
    private String email;
    
    @Schema(description = "DOCUMENT do cliente", example = "123.456.789-00")
    private String document;
    
    @Schema(description = "Telefone do cliente", example = "(11) 98765-4321")
    private String phone;
    
    @Schema(description = "Data e hora de criação do registro", example = "2023-01-01T10:00:00")
    private LocalDateTime createdAt;
    
    @Schema(description = "Data e hora da última atualização", example = "2023-01-10T11:30:00")
    private LocalDateTime updatedAt;
    
    @Schema(description = "Indica se o cliente está ativo", example = "true")
    private boolean active;
} 