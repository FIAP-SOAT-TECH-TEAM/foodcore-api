package com.soat.fiap.food.core.api.catalog.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisições de criação/atualização de catalogos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CatalogCreateRequest", description = "DTO para criação de um catálogo")
public class CatalogRequest {
    
    @NotBlank(message = "O nome da categoria é obrigatório")
    @Size(max = 100, message = "O nome da categoria deve ter no máximo 100 caracteres")
    @Schema(description = "Nome da categoria", example = "Promoções", required = true)
    private String name;
} 