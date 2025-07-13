package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Stock;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.StockDTO;

public class StockDTOMapper {

	/**
	 * Converte um StockDTO para um objeto de dominio Stock.
	 *
	 * @param dto o StockDTO a ser convertido
	 * @return o objeto Stock correspondente
	 */
	public static Stock toDomain(StockDTO dto) {
		return Stock.fromDTO(dto);
	}

	/**
	 * Converte um objeto Stock para um StockDTO.
	 *
	 * @param stock o objeto Stock a ser convertido
	 * @return o StockDTO correspondente
	 */
	public static StockDTO toDTO(Stock stock) {
		return new StockDTO(stock.getId(), stock.getQuantity(), stock.getAuditInfo().getCreatedAt(),
				stock.getAuditInfo().getUpdatedAt());
	}
}
