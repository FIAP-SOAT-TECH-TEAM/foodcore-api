package com.soat.fiap.food.core.api.product.infrastructure.adapters.out.persistence;

import com.soat.fiap.food.core.api.product.application.ports.out.ProductRepository;
import com.soat.fiap.food.core.api.product.domain.model.Product;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.out.persistence.entity.CategoryEntity;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.out.persistence.mapper.ProductEntityMapper;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.out.persistence.repository.SpringDataCategoryRepository;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.out.persistence.repository.SpringDataProductRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador que implementa a porta de saída ProductRepository
 */
@Component
public class ProductRepositoryAdapter implements ProductRepository {

    private final SpringDataProductRepository springDataProductRepository;
    private final SpringDataCategoryRepository springDataCategoryRepository;
    private final ProductEntityMapper productEntityMapper;

    public ProductRepositoryAdapter(
            SpringDataProductRepository springDataProductRepository,
            SpringDataCategoryRepository springDataCategoryRepository,
            ProductEntityMapper productEntityMapper) {
        this.springDataProductRepository = springDataProductRepository;
        this.springDataCategoryRepository = springDataCategoryRepository;
        this.productEntityMapper = productEntityMapper;
    }

    @Override
    public Product save(Product product) {
        var productEntity = productEntityMapper.toEntity(product);
        var savedEntity = springDataProductRepository.save(productEntity);
        return productEntityMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        return springDataProductRepository.findById(id)
                .map(productEntityMapper::toDomain);
    }

    @Override
    public List<Product> findAll() {
        var productEntities = springDataProductRepository.findAllByOrderByDisplayOrderAsc();
        return productEntityMapper.toDomainList(productEntities);
    }

    @Override
    public List<Product> findByCategory(Long categoryId) {
        Optional<CategoryEntity> categoryEntityOptional = springDataCategoryRepository.findById(categoryId);
        
        if (categoryEntityOptional.isEmpty()) {
            return List.of();
        }
        
        var productEntities = springDataProductRepository
                .findByCategoryOrderByDisplayOrderAsc(categoryEntityOptional.get());
        
        return productEntityMapper.toDomainList(productEntities);
    }

    @Override
    public List<Product> findAllActive() {
        var productEntities = springDataProductRepository.findByActiveTrue();
        return productEntityMapper.toDomainList(productEntities);
    }

    @Override
    public void delete(Long id) {
        springDataProductRepository.deleteById(id);
    }
} 