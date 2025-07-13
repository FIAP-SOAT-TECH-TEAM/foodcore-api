package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Stock;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.StockDTO;

public class StockDTOMapper {

	public static Stock toDomain(StockDTO dto) {
		return Stock.fromDTO(dto);
	}

	public static StockDTO toDTO(Stock stock) {
		return new StockDTO(stock.getId(), stock.getQuantity(), stock.getAuditInfo().getCreatedAt(),
				stock.getAuditInfo().getUpdatedAt());
	}
}
