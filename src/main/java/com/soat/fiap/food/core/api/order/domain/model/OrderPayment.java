package com.soat.fiap.food.core.api.order.domain.model;

import com.soat.fiap.food.core.api.order.domain.vo.OrderPaymentStatus;
import com.soat.fiap.food.core.api.shared.exception.BusinessException;
import lombok.*;
import java.time.LocalDateTime;
import java.util.Objects;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

/**
 * Entidade de domínio que representa um pagamento de um pedido
 */
@Getter
@Setter
public class OrderPayment {
    private Long id;
    private Long orderId;
    private Long paymentId;
    private OrderPaymentStatus status = OrderPaymentStatus.PENDING;
    private LocalDateTime paidAt;
    private AuditInfo auditInfo = new AuditInfo();

    /**
     * Construtor que inicializa os dados do pagamento de um pedido
     *
     * @param orderId    ID do pedido associado ao pagamento
     * @param paymentId  ID do pagamento
     * @param status     Status atual do pagamento
     * @throws NullPointerException se qualquer um dos parâmetros for nulo
     */
    public OrderPayment(
            Long orderId,
            Long paymentId,
            OrderPaymentStatus status
    ) {
        validate(orderId, paymentId, status);

        this.orderId = orderId;
        this.paymentId = paymentId;
        this.status = status;
    }

    /**
     * Validação centralizada.
     *
     * @param orderId   ID do pedido
     * @param paymentId ID do pagamento
     * @param status    Status do pagamento
     * @throws NullPointerException se qualquer um dos parâmetros for nulo
     */
    private void validate(
            Long orderId,
            Long paymentId,
            OrderPaymentStatus status
    ) {
        Objects.requireNonNull(orderId, "O ID da ordem não pode ser nulo");
        Objects.requireNonNull(paymentId, "O ID do pagamento da ordem não pode ser nulo");
        Objects.requireNonNull(status, "O status do pagamento da ordem não pode ser nulo");
    }

    /**
     * Verifica se o pagamento está aprovado
     *
     * @return true se aprovado, false caso contrário
     */
    boolean isApproved() {
        return status == OrderPaymentStatus.APPROVED;
    }

    /**
     * Atualiza o campo updatedAt com o horário atual.
     *
     * @throws BusinessException se o horário atual for menor ou igual ao createdAt
     */
    void markUpdatedNow() {
        this.auditInfo.setUpdatedAt(LocalDateTime.now());
    }
}