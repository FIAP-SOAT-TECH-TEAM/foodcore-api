package com.soat.fiap.food.core.api.catalog.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisições de criação/atualização de categorias
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CategoryRequest", description = "DTO para criação ou atualização de categorias de produtos")
public class CategoryRequest {

    @NotBlank(message = "O ID do catalogo da categoria é obrigatório")
    @Schema(description = "Id da categoria", example = "2", required = true)
    private Long catalogId;

    @NotBlank(message = "O nome da categoria é obrigatório")
    @Schema(description = "Nome da categoria", example = "Lanches", required = true)
    private String name;
    
    @Schema(description = "Descrição da categoria", example = "Variedade de hambúrgueres e sanduíches")
    private String description;
    
    @Schema(description = "URL da imagem da categoria", example = "https://storage.example.com/images/lanches.jpg")
    private String imageUrl;
    
    @Schema(description = "Ordem de exibição da categoria", example = "1")
    private Integer displayOrder;
} 