package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CatalogDTO(Long id, String name, List<CategoryDTO> categories, LocalDateTime createdAt,
		LocalDateTime updatedAt) {
}
