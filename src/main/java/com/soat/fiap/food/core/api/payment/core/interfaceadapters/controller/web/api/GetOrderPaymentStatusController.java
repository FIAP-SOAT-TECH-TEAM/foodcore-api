package com.soat.fiap.food.core.api.payment.core.interfaceadapters.controller.web.api;

import com.soat.fiap.food.core.api.payment.core.application.usecases.GetLatestPaymentByOrderIdUseCase;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.presenter.web.api.PaymentPresenter;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.PaymentStatusResponse;

/**
 * Controller: Obter status de pagamento de um pedido.
 */
public class GetOrderPaymentStatusController {

    /**
     * Obtém o status de pagamento de um pedido com base no ID do pedido.
     *
     * @param orderId           ID do pedido.
     * @param paymentDataSource Origem de dados para o gateway de pagamento.
     * @return Objeto {@link PaymentStatusResponse} com o status de pagamento do pedido.
     */
    public static PaymentStatusResponse getOrderPaymentStatus(
            Long orderId,
            PaymentDataSource paymentDataSource) {

        var paymentGateway = new PaymentGateway(paymentDataSource);
        var payment = GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(orderId, paymentGateway);

        return PaymentPresenter.toPaymentStatusResponse(payment);
    }
}