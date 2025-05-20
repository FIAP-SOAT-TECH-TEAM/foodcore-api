package com.soat.fiap.food.core.api.catalog.domain.model;

import com.soat.fiap.food.core.api.catalog.domain.exceptions.ProductException;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.StockException;
import com.soat.fiap.food.core.api.order.domain.model.OrderItem;
import com.soat.fiap.food.core.api.order.domain.vo.OrderNumber;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

import lombok.*;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Entidade de domínio que representa um estoque de produto
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
@NoArgsConstructor
public class Stock {
    private Long id;
    private Integer quantity = 0;
    private final AuditInfo auditInfo = new AuditInfo();

    private Product product;

    public Stock(int quantity) {
        validate(quantity);

        this.quantity = quantity;
    }

    private void validate(
            int quantity
    ) {
        if (quantity < 0) {
            throw new StockException("A quantidade de estoque deve ser maior que 0");
        }
    }

    void setQuantity(int quantity) {
        if (quantity < 0) {
            throw new StockException("A quantidade de estoque deve ser maior que 0");
        }
    }
}
