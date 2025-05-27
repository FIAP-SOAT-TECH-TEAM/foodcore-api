package com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entidade JPA para pedido
 */
@Entity
@Table(name = "orders")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_number")
    private String orderNumber;
    
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private OrderStatusEntity status;
    
    @Column(name = "user_id")
    private Long userId;
    
    @Column(name = "total", nullable = false, precision = 10, scale = 2)
    private BigDecimal totalAmount;
    
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @Builder.Default
    private List<OrderItemEntity> items = new ArrayList<>();
    
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    /**
     * Adiciona um item ao pedido e atualiza o relacionamento
     * 
     * @param item Item a ser adicionado
     */
    public void addItem(OrderItemEntity item) {
        items.add(item);
        item.setOrder(this);
    }
    
    /**
     * Remove um item do pedido
     * 
     * @param item Item a ser removido
     */
    public void removeItem(OrderItemEntity item) {
        items.remove(item);
        item.setOrder(null);
    }
    
    /**
     * Enum que representa os possíveis status de um pedido no banco de dados
     */
    public enum OrderStatusEntity {
        RECEIVED,
        WAITING_PAYMENT,
        PREPARING,
        READY,
        COMPLETED,
        CANCELLED
    }
} 