package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO reoresentando a entidade Catalog.
 * Contem os dados do catalogo incluindo seu ID, nome, categorias,
 * e seus timestamps para auditoria.
 */
public record CatalogDTO(Long id, String name, List<CategoryDTO> categories, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
}
