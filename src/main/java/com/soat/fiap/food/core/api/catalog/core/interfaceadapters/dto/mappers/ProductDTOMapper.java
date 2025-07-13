package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.ProductDTO;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.StockDTO;

public class ProductDTOMapper {

	public static Product toDomain(ProductDTO dto) {
		return Product.fromDTO(dto);
	}

	public static ProductDTO toDTO(Product product) {
		StockDTO stockDTO = StockDTOMapper.toDTO(product.getStock());
		return new ProductDTO(product.getId(), product.getDetails(), product.getImageUrlValue(), product.getPrice(),
				stockDTO, product.getDisplayOrder(), product.isActive(), product.getCreatedAt(),
				product.getUpdatedAt());
	}
}
