package com.soat.fiap.food.core.api.catalog.application.mapper.response;

import com.soat.fiap.food.core.api.catalog.application.dto.response.CategoryResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.shared.ImageUrlMapper;
import com.soat.fiap.food.core.api.catalog.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.domain.vo.ImageUrl;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte a entidade Category para o DTO CategoryResponse
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {ProductResponseMapper.class, ImageUrlMapper.class})
public interface CategoryResponseMapper {

    @Mapping(source = "imageUrl", target = "imageUrl", qualifiedByName = "mapImageUrlToString")
    CategoryResponse toResponse(Category category);

    List<CategoryResponse> toResponseList(List<Category> categories);

}
