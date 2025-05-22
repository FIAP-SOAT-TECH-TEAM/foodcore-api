package com.soat.fiap.food.core.api.payment.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

/**
 * Evento de domínio emitido quando um pagamento é aprovado
 */
@Getter
@AllArgsConstructor
public class PaymentApprovedEvent {
    private final Long paymentId;
    private final LocalDateTime approvedAt;

    /**
     * Cria um evento de pagamento aprovado
     * 
     * @param paymentId     ID do pagamento
     * @return Nova instância do evento
     */
    public static PaymentApprovedEvent of(Long paymentId) {
        return new PaymentApprovedEvent(paymentId, LocalDateTime.now());
    }
}