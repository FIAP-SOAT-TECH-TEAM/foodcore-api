package com.soat.fiap.food.core.api.product.infrastructure.adapters.out.persistence;

import com.soat.fiap.food.core.api.product.application.ports.out.CategoryRepository;
import com.soat.fiap.food.core.api.product.domain.model.Category;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.out.persistence.mapper.CategoryEntityMapper;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.out.persistence.repository.SpringDataCategoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador que implementa a porta de saída CategoryRepository
 */
@Component
public class CategoryRepositoryAdapter implements CategoryRepository {

    private final SpringDataCategoryRepository springDataCategoryRepository;
    private final CategoryEntityMapper categoryEntityMapper;

    public CategoryRepositoryAdapter(
            SpringDataCategoryRepository springDataCategoryRepository,
            CategoryEntityMapper categoryEntityMapper) {
        this.springDataCategoryRepository = springDataCategoryRepository;
        this.categoryEntityMapper = categoryEntityMapper;
    }

    @Override
    public Category save(Category category) {
        var categoryEntity = categoryEntityMapper.toEntity(category);
        var savedEntity = springDataCategoryRepository.save(categoryEntity);
        return categoryEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Category> findById(Long id) {
        return springDataCategoryRepository.findById(id)
                .map(categoryEntityMapper::toDomain);
    }

    @Override
    public List<Category> findAll() {
        var categoryEntities = springDataCategoryRepository.findAllByOrderByDisplayOrderAsc();
        return categoryEntityMapper.toDomainList(categoryEntities);
    }

    @Override
    public void delete(Long id) {
        springDataCategoryRepository.deleteById(id);
    }
} 