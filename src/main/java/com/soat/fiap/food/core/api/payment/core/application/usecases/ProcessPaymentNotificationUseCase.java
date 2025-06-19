package com.soat.fiap.food.core.api.payment.core.application.usecases;

import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentApprovedEvent;
import com.soat.fiap.food.core.api.payment.core.domain.exceptions.PaymentNotFoundException;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.request.AcquirerNotificationRequest;

public class ProcessPaymentNotificationUseCase {

    public void processPaymentNotification(AcquirerNotificationRequest acquirerNotificationRequest) {

        var acquirerPaymentResponse = acquirerSource.getAcquirerPayments(acquirerNotificationRequest.getDataId());
        var longExternalRefernce = Long.parseLong(acquirerPaymentResponse.getExternal_reference());
        var payment = paymentRepository.findTopByOrderIdOrderByIdDesc(longExternalRefernce);

        if (payment.isEmpty()) {
            log.warn("Pagamento não foi encontrado a partir da external_reference! {}", longExternalRefernce);
            throw new PaymentNotFoundException("Pagamento", longExternalRefernce);
        }
        else if(payment.get().getStatus() == PaymentStatus.APPROVED) {
            log.info("Pagamento não foi encontrado a partir da external_reference! {}", longExternalRefernce);
            return;
        }
        // Indica que se trata de uma segunda tentativa de pagamento
        else if(payment.get().getStatus() != PaymentStatus.PENDING) {
            payment.get().setId(null);
        }

        payment.get().setStatus(acquirerPaymentResponse.getStatus());
        payment.get().setType(acquirerPaymentResponse.getPaymentType());
        payment.get().setTid(acquirerNotificationRequest.getDataId());
        paymentRepository.save(payment.get());

        if (acquirerPaymentResponse.getStatus() == PaymentStatus.APPROVED) {
            log.info("Pagamento aprovado: {}, Publicando evento!", payment.get().getId());

            eventPublisher.publishEvent(
                    PaymentApprovedEvent.of(
                            payment.get().getId(),
                            payment.get().getOrderId(),
                            payment.get().getAmount(),
                            payment.get().getTypeName()
                    )
            );

            log.info("Evento de pagamento aprovado publicado! PaymentId: {}, OrderId: {}!", payment.get().getId(), payment.get().getOrderId());
        }
    }
}
