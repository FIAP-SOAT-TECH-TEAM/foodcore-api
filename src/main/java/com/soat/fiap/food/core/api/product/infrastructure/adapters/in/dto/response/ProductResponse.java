package com.soat.fiap.food.core.api.product.infrastructure.adapters.in.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * DTO para respostas de produtos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "ProductResponse", description = "DTO para resposta com informações de produtos")
public class ProductResponse {
    
    @Schema(description = "Identificador único do produto", example = "1")
    private Long id;
    
    @Schema(description = "Nome do produto", example = "X-Burger")
    private String name;
    
    @Schema(description = "Descrição do produto", example = "Hambúrguer com queijo, alface e tomate")
    private String description;
    
    @Schema(description = "Preço do produto", example = "25.90")
    private BigDecimal price;
    
    @Schema(description = "URL da imagem do produto", example = "https://storage.example.com/images/x-burger.jpg")
    private String imageUrl;
    
    @Schema(description = "Categoria do produto")
    private CategoryResponse category;
    
    @Schema(description = "Indica se o produto está ativo", example = "true")
    private boolean active;
    
    @Schema(description = "Ordem de exibição do produto", example = "1")
    private Integer displayOrder;
} 