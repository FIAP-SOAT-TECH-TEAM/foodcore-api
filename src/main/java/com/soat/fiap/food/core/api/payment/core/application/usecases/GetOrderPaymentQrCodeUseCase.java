package com.soat.fiap.food.core.api.payment.core.application.usecases;

import com.soat.fiap.food.core.api.payment.core.domain.exceptions.PaymentNotFoundException;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.QrCodeResponse;

public class GetOrderPaymentQrCodeUseCase {
    public QrCodeResponse getOrderPaymentQrCode(Long orderId) {
        var payment = paymentRepository.findTopByOrderIdOrderByIdDesc(orderId);

        if (payment.isEmpty()) {
            log.warn("Pagamento não foi encontrado a partir do orderId! {}", orderId);
            throw new PaymentNotFoundException("Pagamento", orderId);
        }

        accessManager.validateAccess(payment.get().getUserId());

        return qrCodeResponseMapper.toResponse(payment.get());
    }
}
