package com.soat.fiap.food.core.api.payment.domain.events;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;

/**
 * Evento de domínio emitido quando um pagamento expirou
 */
@Getter
@AllArgsConstructor
public class PaymentExpiredEvent {
    private final Long paymentId;
    private final Long orderId;
    private final OffsetDateTime expiredIn;
} 