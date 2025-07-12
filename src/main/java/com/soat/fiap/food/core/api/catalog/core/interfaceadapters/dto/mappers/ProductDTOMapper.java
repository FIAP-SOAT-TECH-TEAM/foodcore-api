package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.ProductDTO;

public class ProductDTOMapper {

	public static Product toDomain(ProductDTO dto) {
		return Product.fromDTO(dto);
	}

	public static ProductDTO toDTO(Product product) {
		return new ProductDTO(product.getId(), product.getDetails(), product.getImageUrlValue(), product.getPrice(),
				product.getDisplayOrder(), product.getStockQuantity(), product.isActive(), product.getCreatedAt(),
				product.getUpdatedAt());
	}
}
