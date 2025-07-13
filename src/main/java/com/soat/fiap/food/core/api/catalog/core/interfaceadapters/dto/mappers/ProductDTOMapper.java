package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.ProductDTO;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.StockDTO;

public class ProductDTOMapper {

	/**
	 * Converte um ProductDTO para um objeto de dominio Product.
	 *
	 * @param dto o ProductDTO a ser convertido
	 * @return o objeto Product correspondente
	 */
	public static Product toDomain(ProductDTO dto) {
		return Product.fromDTO(dto);
	}

	/**
	 * Converte um objeto Product para um ProductDTO.
	 *
	 * @param product o objeto Product a ser convertido
	 * @return o ProductDTO correspondente
	 */
	public static ProductDTO toDTO(Product product) {
		StockDTO stockDTO = StockDTOMapper.toDTO(product.getStock());
		return new ProductDTO(product.getId(), product.getDetails(), product.getImageUrlValue(), product.getPrice(),
				stockDTO, product.getDisplayOrder(), product.isActive(), product.getCreatedAt(),
				product.getUpdatedAt());
	}
}
