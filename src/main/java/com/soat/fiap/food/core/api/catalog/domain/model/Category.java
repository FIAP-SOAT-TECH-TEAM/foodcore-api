package com.soat.fiap.food.core.api.catalog.domain.model;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Entidade de domínio que representa uma categoria de produto
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class Category {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Integer displayOrder;
    private boolean active;
    private AuditInfo auditInfo;

    private Catalog catalog;
    private List<Product> products;

    Product getProductById (Long productId) {
        Objects.requireNonNull(productId, "O ID do produto não pode ser nulo");

        return products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Produto não encontrado"));

    }

    void addProduct(Product product) {
        Objects.requireNonNull(product, "O produto não pode ser nulo");

        products = (products == null) ? new ArrayList<>() : products;

        if (products.stream().anyMatch(p -> p.getName().equals(product.getName()))) {
            return;
        }

        products.add(product);
    }

    void removeProduct(Product product) {
        Objects.requireNonNull(product, "O produto não pode ser nulo");

        if (products == null || products.stream().noneMatch(p -> p.getName().equals(product.getName()))) {
            return;
        }

        products.remove(product);
    }
} 