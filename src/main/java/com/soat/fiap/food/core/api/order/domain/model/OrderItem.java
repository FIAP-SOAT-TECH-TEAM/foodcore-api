package com.soat.fiap.food.core.api.order.domain.model;

import com.soat.fiap.food.core.api.product.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade de domínio que representa um item de pedido
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Long id;
    private Order order;
    private Long productId;
    private Integer quantity;
    private BigDecimal unitPrice;
    private final BigDecimal subTotal = unitPrice.multiply(BigDecimal.valueOf(quantity));
    private String observations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 