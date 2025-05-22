package com.soat.fiap.food.core.api.catalog.application.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(name = "CatalogResponse", description = "DTO de resposta para um catálogo")
public class CatalogResponse {

    @Schema(description = "ID do catálogo", example = "1")
    private Long id;

    @Schema(description = "Nome do catálogo", example = "Promoções de Verão")
    private String name;

    @Schema(description = "Lista de categorias pertencentes ao catálogo")
    private List<CategoryResponse> categories;
}