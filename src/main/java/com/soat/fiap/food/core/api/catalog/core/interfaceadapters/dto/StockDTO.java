package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto;

import java.time.LocalDateTime;

public record StockDTO(Long id, Integer quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
