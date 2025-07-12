package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.soat.fiap.food.core.api.catalog.core.domain.vo.Details;

public record CategoryDTO(Long id, Details details, String imageUrl, int displayOrder, boolean active,
		List<ProductDTO> products, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
