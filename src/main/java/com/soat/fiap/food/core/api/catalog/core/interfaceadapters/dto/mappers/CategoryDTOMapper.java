package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.core.domain.vo.ImageUrl;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.CategoryDTO;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.ProductDTO;
import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;

public class CategoryDTOMapper {

	/**
	 * Converts a CategoryDTO to a Category domain object.
	 *
	 * @param dto
	 *            the CategoryDTO to be converted
	 * @return the corresponding Category domain object
	 */
	public static Category toDomain(CategoryDTO dto) {
		Objects.requireNonNull(dto, "O DTO da categoria não pode ser nulo");

		ImageUrl imageUrl = new ImageUrl(dto.imageUrl());

		Category category = new Category(dto.details(), imageUrl, dto.displayOrder(), dto.active());
		category.setId(dto.id());

		if (dto.products() != null) {
			for (ProductDTO productDTO : dto.products()) {
				category.addProduct(ProductDTOMapper.toDomain(productDTO));
			}
		}

		if (dto.createdAt() != null && dto.updatedAt() != null) {
			category.setAuditInfo(new AuditInfo(dto.createdAt(), dto.updatedAt()));
		}

		return category;
	}

	/**
	 * Converte a Category domain object para CategoryDTO.
	 *
	 * @param category
	 *            categoria a ser convertida
	 * @return o CategoryDTO correspondente
	 */
	public static CategoryDTO toDTO(Category category) {
		List<ProductDTO> products = category.getProducts()
				.stream()
				.map(ProductDTOMapper::toDTO)
				.collect(Collectors.toList());

		return new CategoryDTO(category.getId(), category.getDetails(), category.getImageUrlValue(),
				category.getDisplayOrder(), category.isActive(), products, category.getCreatedAt(),
				category.getUpdatedAt());
	}
}
