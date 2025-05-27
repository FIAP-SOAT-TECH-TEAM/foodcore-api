package com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.entity;

import com.soat.fiap.food.core.api.order.domain.vo.OrderItemPrice;
import com.soat.fiap.food.core.api.shared.vo.AuditInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Entity
@Table(name = "order_items")
@Getter
@Setter
public class OrderItemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(name = "product_id", nullable = false)
    private Long productId;

    @Embedded
    private OrderItemPrice orderItemPrice;

    @Column(columnDefinition = "TEXT")
    private String observations = "";

    @Embedded
    private AuditInfo auditInfo = new AuditInfo();
}