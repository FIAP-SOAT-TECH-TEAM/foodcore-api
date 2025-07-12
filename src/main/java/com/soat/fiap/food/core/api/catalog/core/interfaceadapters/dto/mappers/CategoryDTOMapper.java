package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.CategoryDTO;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.ProductDTO;

public class CategoryDTOMapper {

	public static Category toDomain(CategoryDTO dto) {
		return Category.fromDTO(dto);
	}

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
