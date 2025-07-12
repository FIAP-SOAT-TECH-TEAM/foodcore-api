package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto;

import java.time.LocalDateTime;
import java.util.List;

public record CategoryDTO(Long id, String name, String description, String imageUrl, int displayOrder, boolean active,
		List<ProductDTO> products, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
