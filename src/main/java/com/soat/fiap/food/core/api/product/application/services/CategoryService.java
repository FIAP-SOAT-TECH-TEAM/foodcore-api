package com.soat.fiap.food.core.api.product.application.services;

import com.soat.fiap.food.core.api.product.application.ports.in.CategoryUseCase;
import com.soat.fiap.food.core.api.product.application.ports.out.CategoryRepository;
import com.soat.fiap.food.core.api.product.domain.model.Category;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementação do caso de uso de Categoria
 */
@Service
public class CategoryService implements CategoryUseCase {

    private final CategoryRepository categoryRepository;

    public CategoryService(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        category.activate();
        return categoryRepository.save(category);
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, Category category) {
        Optional<Category> existingCategory = categoryRepository.findById(id);
        
        if (existingCategory.isEmpty()) {
            throw new RuntimeException("Categoria não encontrada com ID: " + id);
        }
        
        category.setId(id);
        return categoryRepository.save(category);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        categoryRepository.delete(id);
    }
} 