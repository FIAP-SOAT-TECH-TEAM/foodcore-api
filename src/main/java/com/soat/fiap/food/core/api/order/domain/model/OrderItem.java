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
    private Product product;
    private Integer quantity;
    private BigDecimal unitPrice;
    private String observations;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
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
    
    /**
     * Obtém o ID do produto
     * @return ID do produto ou null se o produto não estiver definido
     */
    public Long getProductId() {
        return product != null ? product.getId() : null;
    }
    
    /**
     * Obtém o nome do produto
     * @return Nome do produto ou null se o produto não estiver definido
     */
    public String getProductName() {
        return product != null ? product.getName() : null;
    }
} 