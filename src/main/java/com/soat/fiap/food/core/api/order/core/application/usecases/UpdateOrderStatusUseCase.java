package com.soat.fiap.food.core.api.order.core.application.usecases;

import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderNotFoundException;
import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.gateways.OrderGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Atualizar status do pedido.
 */
@Slf4j
public class UpdateOrderStatusUseCase {

    /**
     * Atualiza o status de um pedido
     *
     * @param orderId ID do pedido
     * @param orderStatus novo status do pedido
     * @param gateway Gateway para comunicação com o mundo exterior
     * @return Pedido atualizado
     */
    public static Order updateOrderStatus(
            Long orderId,
            OrderStatus orderStatus,
            OrderGateway gateway) {

        var order = gateway.findById(orderId);

        if (order.isEmpty()) {
            throw new OrderNotFoundException("Pedido", orderId);
        }
        else if (order.get().getOrderStatus() == orderStatus) {
            return order.get();
        }

        order.get().setOrderStatus(orderStatus);

        return order.get();
    }
}
