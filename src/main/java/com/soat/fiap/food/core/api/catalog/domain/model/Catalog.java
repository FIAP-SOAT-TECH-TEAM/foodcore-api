package com.soat.fiap.food.core.api.catalog.domain.model;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entidade de domínio que representa um catalogo de produto
 * AGGREGATE ROOT:
 *  - Toda modificação de entidades internas do agregado devem passar pela entidade raíz;
 *  - Único ponto de entrada para qualquer entidade interna do agregado (Lei de Demeter);
 *  - Entidades dentro deste agregado podem se referenciar via id ou objeto;
 *  - Entidades de outros agregados só podem referenciar esta entidade raiz, e isto deve ser via Id;
 */
@Data
public class Catalog {
    private Long id;
    private String name;
    private final AuditInfo auditInfo = new AuditInfo();

    private List<Category> categories;

    public Category getCategoryById(Long categoryId) {
        Objects.requireNonNull(categoryId, "O ID da categoria não pode ser nulo");

        return categories.stream()
                .filter(o -> o.getId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Categoria não encontrada"));
    }

    public Product getProductFromCategoryById(Long categoryId, Long productId) {
        Objects.requireNonNull(categoryId, "O ID da categoria não pode ser nulo");
        Objects.requireNonNull(productId, "O ID do produto não pode ser nulo");

        var category = getCategoryById(categoryId);

        return category.getProductById(productId);
    }

    public void addCategory(Category category) {

        Objects.requireNonNull(category, "A categoria não pode ser nula");

        categories = (categories == null) ? new ArrayList<>() : categories;

        if (categories
                .stream()
                .anyMatch(c -> c.getName().equals(category.getName()))) {
            return;
        }

        categories.add(category);
    }

    public void updateCategory(Category category) {

        Objects.requireNonNull(category, "A categoria não pode ser nula");

        categories = (categories == null) ? new ArrayList<>() : categories;

        if (categories
                .stream()
                .anyMatch(c -> c.getName().equals(category.getName()))) {
            return;
        }

        categories.add(category);
    }

    public void removeCategory(Long categoryId) {

        Objects.requireNonNull(categoryId, "O ID da categoria não pode ser nula");

        var category = getCategoryById(categoryId);

        categories.remove(category);
    }

    public void addProductToCategory(Long categoryId, Product product) {
        Objects.requireNonNull(categoryId, "O ID da categoria não pode ser nulo");
        Objects.requireNonNull(product, "O produto não pode ser nulo");

        var category = getCategoryById(categoryId);

        category.addProduct(product);
    }


    public void updateProductInCategory(Long currentCategoryId, Long newCategoryId, Product newProduct) {

        Objects.requireNonNull(currentCategoryId, "O ID da categoria não pode ser nulo");
        Objects.requireNonNull(newCategoryId, "O novo ID da categoria não pode ser nulo");
        Objects.requireNonNull(newProduct, "O produto não pode ser nulo");

        var currentProduct = getProductFromCategoryById(currentCategoryId, newCategoryId);

        currentProduct.setName(newProduct.getName());
        currentProduct.setDescription(newProduct.getDescription());
        currentProduct.setPrice(newProduct.getPrice());
        currentProduct.setImageUrl(newProduct.getImageUrl());
        currentProduct.setDisplayOrder(newProduct.getDisplayOrder());
        currentProduct.setActive(newProduct.isActive());

        if (!currentCategoryId.equals(newCategoryId)) {
            removeProductFromCategory(currentCategoryId, newProduct);
            addProductToCategory(newCategoryId, newProduct);
        }

    }

    public void removeProductFromCategory (Long categoryId, Product product) {
        Objects.requireNonNull(categoryId, "O ID da categoria não pode ser nulo");
        Objects.requireNonNull(product, "O produto não pode ser nulo");

        var category = getCategoryById(categoryId);

        category.removeProduct(product);
    }


} 