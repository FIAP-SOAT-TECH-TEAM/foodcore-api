package com.soat.fiap.food.core.api.order.domain.model;

import com.soat.fiap.food.core.api.payment.domain.model.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade de domínio que representa um item de pedido
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderPayment {
    private Long id;
    private Long orderId;
    private Long paymentId;
    private PaymentStatus status;
    private LocalDateTime paidAt;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    /**
     * Atualiza o status do pagamento
     * 
     * @param newStatus Novo status
     */
    public void updateStatus(PaymentStatus newStatus) {
        this.status = newStatus;
        if (newStatus == PaymentStatus.APPROVED ||
                newStatus == PaymentStatus.REJECTED ||
                newStatus == PaymentStatus.CANCELLED) {

            this.paidAt = LocalDateTime.now();
        }
    }

    /**
     * Verifica se o pagamento está aprovado
     * 
     * @return true se aprovado, false caso contrário
     */
    public boolean isApproved() {
        return status == PaymentStatus.APPROVED;
    }
}