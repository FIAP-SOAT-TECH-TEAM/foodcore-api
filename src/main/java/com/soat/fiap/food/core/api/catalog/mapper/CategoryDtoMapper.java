package com.soat.fiap.food.core.api.catalog.mapper;

import com.soat.fiap.food.core.api.catalog.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.in.dto.request.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.in.dto.response.CategoryResponse;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper que converte entre DTOs e entidades de domínio para Category
 */
@Component
public class CategoryDtoMapper {
    
    /**
     * Converte entidade de domínio para DTO de resposta
     * @param category Entidade de domínio
     * @return DTO de resposta
     */
    public CategoryResponse toResponse(Category category) {
        if (category == null) {
            return null;
        }
        
        CategoryResponse response = new CategoryResponse();
        response.setId(category.getId());
        response.setName(category.getName());
        response.setDescription(category.getDescription());
        response.setImageUrl(category.getImageUrl());
        response.setDisplayOrder(category.getDisplayOrder());
        response.setActive(category.isActive());
        
        return response;
    }
    
    /**
     * Converte lista de entidades de domínio para lista de DTOs de resposta
     * @param categories Lista de entidades de domínio
     * @return Lista de DTOs de resposta
     */
    public List<CategoryResponse> toResponseList(List<Category> categories) {
        if (categories == null) {
            return null;
        }
        
        List<CategoryResponse> responseList = new ArrayList<>(categories.size());
        for (Category category : categories) {
            responseList.add(toResponse(category));
        }
        
        return responseList;
    }
    
    /**
     * Converte DTO de requisição para entidade de domínio
     * @param request DTO de requisição
     * @return Entidade de domínio
     */
    public Category toDomain(CategoryRequest request) {
        if (request == null) {
            return null;
        }
        
        Category category = new Category();
        updateDomainFromRequest(request, category);
        category.setActive(true);
        
        return category;
    }
    
    /**
     * Atualiza uma entidade de domínio com os dados de um DTO de requisição
     * @param request DTO de requisição com os dados atualizados
     * @param category Entidade de domínio a ser atualizada
     */
    public void updateDomainFromRequest(CategoryRequest request, Category category) {
        if (request == null || category == null) {
            return;
        }
        
        category.setName(request.getName());
        category.setDescription(request.getDescription());

        if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
            category.setImageUrl(request.getImageUrl());
        }
        
        category.setDisplayOrder(request.getDisplayOrder());
    }
} 