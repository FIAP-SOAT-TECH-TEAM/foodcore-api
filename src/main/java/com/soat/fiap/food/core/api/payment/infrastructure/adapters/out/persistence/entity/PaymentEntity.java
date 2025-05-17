package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.persistence.entity;

import com.soat.fiap.food.core.api.payment.domain.model.PaymentMethod;
import com.soat.fiap.food.core.api.order.domain.vo.OrderPaymentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade JPA que representa um pagamento no banco de dados
 */
@Entity
@Table(name = "payments")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "order_id", nullable = false)
    private Long orderId;
    
    @Column(name = "external_id")
    private String externalId;
    
    @Column(nullable = false)
    private BigDecimal amount;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "type", nullable = false)
    private PaymentMethod method;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderPaymentStatus status;
    
    @Column(name = "qr_code_url")
    private String qrCodeUrl;
    
    @Column(name = "qr_code_data")
    private String qrCodeData;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = this.createdAt;
    }
    
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }
} 