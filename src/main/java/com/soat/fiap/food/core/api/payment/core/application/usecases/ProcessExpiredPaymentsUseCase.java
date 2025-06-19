package com.soat.fiap.food.core.api.payment.core.application.usecases;

import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentExpiredEvent;
import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;

import java.time.LocalDateTime;

public class ProcessExpiredPaymentsUseCase {

    public void processExpiredPayments() {

        var expiredPayments = paymentRepository.findExpiredPaymentsWithoutApprovedOrCancelled(LocalDateTime.now());

        if (expiredPayments.isEmpty()) {
            log.info("Nenhum pagamento pendente expirado encontrado!");
            return;
        }

        for (Payment expiredPayment: expiredPayments) {
            log.info("Iniciando processamento expirado: {}, pedido: {}, expirado em: {}", expiredPayment.getId(), expiredPayment.getOrderId(), expiredPayment.getExpiresIn().toString());
            expiredPayment.setStatus(PaymentStatus.CANCELLED);
            paymentRepository.save(expiredPayment);

            log.info("Pagamento expirado cancelado: {}", expiredPayment.getId());

            var expiredEvent = new PaymentExpiredEvent(expiredPayment.getId(), expiredPayment.getOrderId(), expiredPayment.getExpiresIn());
            eventPublisher.publishEvent(expiredEvent);

            log.info("Evento de pagamento expirado publicado: {}", expiredPayment.getId());
        }
    }
}
