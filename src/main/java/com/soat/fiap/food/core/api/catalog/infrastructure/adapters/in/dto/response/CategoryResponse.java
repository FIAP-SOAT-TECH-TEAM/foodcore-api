package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.in.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para respostas de categorias
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CategoryResponse", description = "DTO para resposta com informações de categorias de produtos")
public class CategoryResponse {
    
    @Schema(description = "Identificador único da categoria", example = "1")
    private Long id;
    
    @Schema(description = "Nome da categoria", example = "Lanches")
    private String name;
    
    @Schema(description = "Descrição da categoria", example = "Variedade de hambúrgueres e sanduíches")
    private String description;
    
    @Schema(description = "URL da imagem da categoria", example = "https://storage.example.com/images/lanches.jpg")
    private String imageUrl;
    
    @Schema(description = "Ordem de exibição da categoria", example = "1")
    private Integer displayOrder;
    
    @Schema(description = "Indica se a categoria está ativa", example = "true")
    private boolean active;
} 