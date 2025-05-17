package com.soat.fiap.food.core.api.order.domain.model;

import com.soat.fiap.food.core.api.order.domain.vo.OrderPaymentStatus;
import lombok.*;
import java.time.LocalDateTime;
import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

/**
 * Entidade de domínio que representa um item de pedido
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class OrderPayment {
    private Long id;
    private Long orderId;
    private Long paymentId;
    private OrderPaymentStatus status = OrderPaymentStatus.PENDING;
    private LocalDateTime paidAt;
    private AuditInfo auditInfo;

    /**
     * Verifica se o pagamento está aprovado
     * 
     * @return true se aprovado, false caso contrário
     */
    boolean isApproved() {
        return status == OrderPaymentStatus.APPROVED;
    }
}