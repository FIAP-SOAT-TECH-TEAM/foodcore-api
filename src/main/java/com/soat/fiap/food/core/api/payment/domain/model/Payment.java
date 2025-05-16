package com.soat.fiap.food.core.api.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade de domínio que representa um pagamento
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long id;
    private Long customerId;
    private PaymentMethod type;
    private LocalDateTime expiresIn;
    private String tid;
    private BigDecimal amount;
    private String qrCodeUrl;
    private String observations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    // /**
    //  * Atualiza o status do pagamento
    //  * @param newStatus Novo status
    //  */
    // public void updateStatus(PaymentStatus newStatus) {
    //     this.status = newStatus;
    //     if (newStatus == PaymentStatus.APPROVED || 
    //         newStatus == PaymentStatus.REJECTED || 
    //         newStatus == PaymentStatus.CANCELLED) {
    //         this.processedAt = LocalDateTime.now();
    //     }
    // }
    
    // /**
    //  * Verifica se o pagamento está aprovado
    //  * @return true se aprovado, false caso contrário
    //  */
    // public boolean isApproved() {
    //     return status == PaymentStatus.APPROVED;
    // }
} 