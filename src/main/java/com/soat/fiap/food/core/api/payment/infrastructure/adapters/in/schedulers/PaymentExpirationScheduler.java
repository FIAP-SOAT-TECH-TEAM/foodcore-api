package com.soat.fiap.food.core.api.payment.infrastructure.adapters.in.schedulers;

import com.soat.fiap.food.core.api.payment.application.ports.in.PaymentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderExpirationScheduler {

    private final PaymentUseCase paymentUseCase;

    @Scheduled(fixedRate = 60_000) //
    public void verificarPedidosExpirados() {
        paymentUseCase.marcarPedidosExpirados();
    }
}
