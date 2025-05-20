package com.soat.fiap.food.core.api.catalog.domain.model;

import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogException;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CategoryException;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.ProductException;
import com.soat.fiap.food.core.api.catalog.domain.vo.Details;
import com.soat.fiap.food.core.api.catalog.domain.vo.ImageUrl;
import com.soat.fiap.food.core.api.order.domain.model.OrderItem;
import com.soat.fiap.food.core.api.order.domain.vo.OrderNumber;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

import lombok.*;
import org.apache.commons.lang3.Validate;

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
    private Details details;
    private ImageUrl imageUrl;
    private Integer displayOrder;
    private boolean active;
    private final AuditInfo auditInfo = new AuditInfo();

    private Catalog catalog;
    private List<Product> products;

    public Category(
            Details details,
            ImageUrl imageUrl,
            int displayOrder,
            boolean active
    ) {
        validate(details, imageUrl, displayOrder);
        this.details = details;
        this.imageUrl = imageUrl;
        this.displayOrder = displayOrder;
        this.active = active;
    }

    private void validate(
            Details details,
            ImageUrl imageUrl,
            int displayOrder
    ) {
        Objects.requireNonNull(details, "Os detalhes da categoria não podem ser nulos");
        Objects.requireNonNull(imageUrl, "A URL da imagem não pode ser nula");

        if (displayOrder < 0) {
            throw new CatalogException("A ordem de exibição da categoria deve ser maior que 0");
        }

    }

    String getName() {
        return this.details.name();
    }

    String getDescription() {
        return this.details.description();
    }

    Product getProductById (Long productId) {
        Objects.requireNonNull(productId, "O ID do produto não pode ser nulo");

        return products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductException("Produto não encontrado"));

    }

    void addProduct(Product product) {
        Objects.requireNonNull(product, "O produto não pode ser nulo");

        products = (products == null) ? new ArrayList<>() : products;

        if (products.stream().anyMatch(p -> p.getName().equals(product.getName()))) {
            throw new CategoryException(String.format("Já existe um produto cadastrado com o nome: %s, na categoria", product.getName()));
        }

        products.add(product);
    }

    void removeProduct(Product product) {
        Objects.requireNonNull(product, "O produto não pode ser nulo");

        if (products == null || products.stream().noneMatch(p -> p.getName().equals(product.getName()))) {
            throw new CategoryException("Produto não encontrado");
        }

        products.remove(product);
    }
} 