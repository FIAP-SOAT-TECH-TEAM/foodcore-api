package com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.entity;

import com.soat.fiap.food.core.api.order.domain.vo.OrderPaymentStatus;
import com.soat.fiap.food.core.api.shared.vo.AuditInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "order_payments", uniqueConstraints = {
        @UniqueConstraint(name = "un_order_payment", columnNames = {"order_id", "payment_id"})
})
@Getter
@Setter
public class OrderPaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private OrderEntity order;

    @Column(name = "payment_id", nullable = false)
    private Long paymentId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderPaymentStatus status = OrderPaymentStatus.PENDING;

    @Column(name = "paid_at")
    private LocalDateTime paidAt;

    @Embedded
    private AuditInfo auditInfo = new AuditInfo();
}