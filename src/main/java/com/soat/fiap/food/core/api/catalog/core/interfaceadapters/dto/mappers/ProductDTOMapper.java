package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers;

import java.util.Objects;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Stock;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.ImageUrl;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.ProductDTO;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.StockDTO;
import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;

public class ProductDTOMapper {

	/**
	 * Converte um ProductDTO para um objeto de dominio Product.
	 *
	 * @param dto
	 *            o ProductDTO a ser convertido
	 * @return o objeto Product correspondente
	 */
	public static Product toDomain(ProductDTO dto) {
		Objects.requireNonNull(dto, "O DTO do produto não pode ser nulo");

		ImageUrl imageUrl = new ImageUrl(dto.imageUrl());

		Product product = new Product(dto.details(), dto.price(), imageUrl, dto.displayOrder());
		product.setId(dto.id());

		if (dto.stock() != null) {
			Stock stock = StockDTOMapper.toDomain(dto.stock());
			product.setStock(stock);
		} else {
			product.setStockQuantity(0);
		}
		product.setActive(dto.active());

		if (dto.createdAt() != null && dto.updatedAt() != null) {
			product.setAuditInfo(new AuditInfo(dto.createdAt(), dto.updatedAt()));
		}

		return product;
	}

	/**
	 * Converte um objeto Product para um ProductDTO.
	 *
	 * @param product
	 *            o objeto Product a ser convertido
	 * @return o ProductDTO correspondente
	 */
	public static ProductDTO toDTO(Product product) {
		StockDTO stockDTO = StockDTOMapper.toDTO(product.getStock());
		return new ProductDTO(product.getId(), product.getDetails(), product.getImageUrlValue(), product.getPrice(),
				stockDTO, product.getDisplayOrder(), product.isActive(), product.getCreatedAt(),
				product.getUpdatedAt());
	}
}
