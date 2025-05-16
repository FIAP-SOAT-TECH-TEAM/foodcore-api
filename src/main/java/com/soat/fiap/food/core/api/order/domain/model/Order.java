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
    private BigDecimal amount;
    
    @Builder.Default
    private List<OrderItem> items = new ArrayList<>();
    
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * Obtém o ID do cliente (se disponível)
     * @return ID do cliente ou null se não houver cliente associado
     */
    public Long getCustomerId() {
        return customer != null ? customer.getId() : null;
    }
    
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
     * @throws IllegalArgumentException se o status for inválido
     * @throws IllegalStateException se a transição de status não for permitida
     */
    public void updateStatus(OrderStatus newStatus) {
        if (newStatus == null) {
            throw new IllegalArgumentException("O status não pode ser nulo");
        }
        
        if (this.status == newStatus) {
            return;
        }
        
        validateStatusTransition(newStatus);
        
        this.status = newStatus;
        this.updatedAt = LocalDateTime.now();
    }
    
    /**
     * Valida se a transição de status é permitida
     * @param newStatus Novo status a ser validado
     * @throws IllegalStateException se a transição não for permitida
     */
    private void validateStatusTransition(OrderStatus newStatus) {
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException(
                "Não é possível alterar o status de um pedido cancelado"
            );
        }
    }
} 