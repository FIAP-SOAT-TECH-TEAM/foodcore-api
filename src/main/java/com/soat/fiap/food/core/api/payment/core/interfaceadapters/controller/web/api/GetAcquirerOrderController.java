package com.soat.fiap.food.core.api.payment.core.interfaceadapters.controller.web.api;

import com.soat.fiap.food.core.api.payment.core.application.usecases.GetAcquirerOrderUseCase;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.AcquirerGateway;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.presenter.web.api.PaymentPresenter;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.AcquirerOrderResponse;

/**
 * Controller: Obter os detalhes de um pedido no adquirente.
 */
public class GetAcquirerOrderController {

    /**
     * Recupera os detalhes de um pedido com base no ID do pedido do adquirente.
     *
     * @param orderId         ID do pedido no adquirente.
     * @param acquirerSource gateway para comunicação com o adquirente
     * @return Objeto {@link AcquirerOrderResponse} contendo os detalhes do pedido.
     */
    public static AcquirerOrderResponse getAcquirerOrder(
            Long orderId,
            AcquirerSource acquirerSource) {

        var acquirerGateway = new AcquirerGateway(acquirerSource);
        var order = GetAcquirerOrderUseCase.getAcquirerOrder(orderId, acquirerGateway);

        return PaymentPresenter.toAcquirerOrderResponse(order);
    }
}