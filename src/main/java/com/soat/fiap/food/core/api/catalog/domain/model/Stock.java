package com.soat.fiap.food.core.api.catalog.domain.model;

import com.soat.fiap.food.core.api.catalog.domain.exceptions.ProductException;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.StockException;
import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

import lombok.*;

import java.math.BigDecimal;

/**
 * Entidade de domínio que representa um estoque de produto
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class Stock {
    private Long id;
    private Integer quantity = 0;
    private final AuditInfo auditInfo = new AuditInfo();

    private Product product;

    void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new StockException("A quantidade de estoque deve ser maior que 0");
        }
    }
}
