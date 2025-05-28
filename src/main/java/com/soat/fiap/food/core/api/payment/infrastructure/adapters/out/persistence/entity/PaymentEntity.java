package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.persistence.entity;

import com.soat.fiap.food.core.api.payment.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.api.payment.domain.vo.QrCode;
import com.soat.fiap.food.core.api.shared.vo.AuditInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * Entidade JPA para pagamento
 */
@Entity
@Table(name = "payments")
@Getter
@Setter
public class PaymentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id")
    private Long userId;

    @Column(name = "order_id")
    private Long orderId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod type;

    @Column(name = "expires_in", nullable = false)
    private OffsetDateTime expiresIn;

    @Column(nullable = false, unique = true)
    private String tid;

    @Column(nullable = false)
    private BigDecimal amount;

    @Embedded
    private QrCode qrCode;

    @Column(columnDefinition = "TEXT")
    private String observations;

    @Embedded
    private AuditInfo auditInfo = new AuditInfo();
}