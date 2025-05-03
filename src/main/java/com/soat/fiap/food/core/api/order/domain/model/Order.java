package com.soat.fiap.food.core.api.order.domain.model;

import com.soat.fiap.food.core.api.customer.domain.model.Customer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade de domínio que representa um pedido
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private String orderNumber;
    private OrderStatus status;
    private Customer customer;
    private BigDecimal totalAmount;
    private List<OrderItem> items = new ArrayList<>();
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * Adiciona um item ao pedido
     * @param item Item a ser adicionado
     */
    public void addItem(OrderItem item) {
        if (items == null) {
            items = new ArrayList<>();
        }
        items.add(item);
        calculateTotalAmount();
    }
    
    /**
     * Remove um item do pedido
     * @param item Item a ser removido
     */
    public void removeItem(OrderItem item) {
        if (items != null) {
            items.remove(item);
            calculateTotalAmount();
        }
    }
    
    /**
     * Calcula o valor total do pedido
     */
    public void calculateTotalAmount() {
        if (items == null || items.isEmpty()) {
            totalAmount = BigDecimal.ZERO;
            return;
        }
        
        totalAmount = items.stream()
                .map(OrderItem::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    /**
     * Atualiza o status do pedido
     * @param newStatus Novo status
     */
    public void updateStatus(OrderStatus newStatus) {
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }
} 