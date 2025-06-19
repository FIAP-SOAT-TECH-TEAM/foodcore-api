package com.soat.fiap.food.core.api.payment.core.application.usecases;

import com.soat.fiap.food.core.api.payment.core.domain.exceptions.PaymentNotFoundException;
import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Obter o status de pagamento de um pedido.
 */
@Slf4j
public class GetLatestPaymentByOrderIdUseCase {

    /**
     * Recupera o pagamento mais recente associado ao pedido informado.
     *
     * @param orderId        ID do pedido.
     * @param gateway Gateway responsável por acessar os dados de pagamento.
     * @return Instância de {@link Payment} com o status mais recente.
     * @throws PaymentNotFoundException caso nenhum pagamento seja encontrado para o pedido.
     */
    public static Payment getLatestPaymentByOrderId(Long orderId, PaymentGateway gateway) {
        var payment = gateway.findTopByOrderIdOrderByIdDesc(orderId);

        if (payment.isEmpty()) {
            log.warn("Pagamento não foi encontrado a partir do orderId! {}", orderId);
            throw new PaymentNotFoundException("Pagamento", orderId);
        }

        accessManager.validateAccess(payment.get().getUserId());

        return payment.get();
    }
}
