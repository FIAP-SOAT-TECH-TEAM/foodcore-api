package com.soat.fiap.food.core.api.catalog.domain.model;

import com.soat.fiap.food.core.api.catalog.domain.exceptions.ProductException;
import com.soat.fiap.food.core.api.catalog.domain.vo.Details;
import com.soat.fiap.food.core.api.catalog.domain.vo.ImageUrl;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

/**
 * Entidade de domínio que representa um produto
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class Product {

    private Long id;
    private Details details;
    private BigDecimal price;
    private ImageUrl imageUrl;
    private boolean active;
    private Integer displayOrder;
    private final AuditInfo auditInfo = new AuditInfo();

    private Category category;
    private final Stock stock = new Stock();

    String getName() {
        return this.details.name();
    }

    String getDescription() {
        return this.details.description();
    }


    void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductException("O preço deve ser maior que 0");
        }

        this.price = price;
    }

    void updateStockQuantity (int quantity) {
        stock.setQuantity(quantity);
    }

    /**
     * Verifica se o produto está disponível para venda.
     * 
     * @return true se o produto estiver ativo e com preço válido.
     */
    boolean isActive() {
        return active && price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }
} 