package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.CatalogDTO;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.CategoryDTO;

public class CatalogDTOMapper {

	public static Catalog toDomain(CatalogDTO dto) {
		List<Category> categories = dto.categories()
				.stream()
				.map(CategoryDTOMapper::toDomain)
				.collect(Collectors.toList());

		return Catalog.fromDTO(dto);
	}

	public static CatalogDTO toDTO(Catalog catalog) {
		List<CategoryDTO> categoryDTOs = catalog.getCategories()
				.stream()
				.map(CategoryDTOMapper::toDTO)
				.collect(Collectors.toList());

		return new CatalogDTO(catalog.getId(), catalog.getName(), categoryDTOs, catalog.getCreatedAt(),
				catalog.getUpdatedAt());
	}
}
