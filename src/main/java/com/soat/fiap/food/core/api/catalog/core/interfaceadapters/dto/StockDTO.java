package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto;

import java.time.LocalDateTime;

/**
 * DTO representando a entidade Stock. Contém os dados de estoque incluindo o
 * ID, quantidade, e timestamps para auditoria.
 */
public record StockDTO(Long id, Integer quantity, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
