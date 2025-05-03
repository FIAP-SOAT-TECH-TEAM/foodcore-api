package com.soat.fiap.food.core.api.order.domain.model;

import com.soat.fiap.food.core.api.product.domain.model.Product;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Entidade de domínio que representa um item de pedido
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderItem {
    private Long id;
    private Product product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String observations;
    
    /**
     * Calcula o subtotal do item (quantidade * preço unitário)
     * @return Subtotal do item
     */
    public BigDecimal getSubtotal() {
        if (quantity == null || unitPrice == null) {
            return BigDecimal.ZERO;
        }
        return unitPrice.multiply(BigDecimal.valueOf(quantity));
    }
} 