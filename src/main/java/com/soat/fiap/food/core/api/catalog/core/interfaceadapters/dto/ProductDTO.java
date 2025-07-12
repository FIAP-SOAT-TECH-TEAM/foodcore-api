package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ProductDTO(Long id, String name, String description, String imageUrl, BigDecimal price,
		Integer stockQuantity, Integer displayOrder, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
