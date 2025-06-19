package com.soat.fiap.food.core.api.payment.core.application.usecases;

import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderNotFoundException;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.AcquirerGateway;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.AcquirerOrderResponse;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class GetAcquirerOrderUseCase {

    public static AcquirerOrderResponse getAcquirerOrder(Long merchantOrder, AcquirerGateway gateway) {
        var order = gateway.getAcquirerOrder(merchantOrder);

        if (order == null) {
            log.warn("Pedido não foi encontrado no adquirente! Merchant Order: {}", merchantOrder);
            throw new OrderNotFoundException("Pedido adquirente", merchantOrder);
        }

        return order;
    }
}
