package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.soat.fiap.food.core.api.catalog.core.domain.vo.Details;

/**
 * DTO representando a entidade Product.
 * Contém os dados do produto incluindo seu ID, detalhes,
 * URL da imagem, preço, informações de estoque, ordem de exibição,
 * status ativo e timestamps para auditoria.
 */
public record ProductDTO(Long id, Details details, String imageUrl, BigDecimal price, StockDTO stock,
		Integer displayOrder, boolean active, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
