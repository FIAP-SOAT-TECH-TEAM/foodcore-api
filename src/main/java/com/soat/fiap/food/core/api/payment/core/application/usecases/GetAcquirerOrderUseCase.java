package com.soat.fiap.food.core.api.payment.core.application.usecases;

import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderNotFoundException;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.AcquirerOrderResponse;

public class GetAcquirerOrderUseCase {

    public AcquirerOrderResponse getAcquirerOrder(Long merchantOrder) {
        var order = acquirerSource.getAcquirerOrder(merchantOrder);

        if (order == null) {
            log.warn("Pedido não foi encontrado no adquirente! Merchant Order: {}", merchantOrder);
            throw new OrderNotFoundException("Pedido adquirente", merchantOrder);
        }

        return order;
    }

}
