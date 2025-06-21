package com.soat.fiap.food.core.api.order.core.application.usecases;

import com.soat.fiap.food.core.api.order.core.domain.events.OrderCanceledEvent;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderItemCanceledEvent;
import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.EventPublisherGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Caso de uso: publicar o evento {@link OrderCanceledEvent}
 * com os itens do pedido como {@link OrderItemCanceledEvent}.
 */
@Slf4j
public class PublishOrderCanceledEventUseCase {

    /**
     * Publica o evento {@link OrderCanceledEvent}, incluindo os itens do pedido
     * como eventos do tipo {@link OrderItemCanceledEvent}.
     *
     * @param order   O pedido cancelado que será convertido em evento.
     * @param gateway O gateway responsável por publicar o evento.
     */
    public static void publishOrderCanceledEvent(Order order, EventPublisherGateway gateway) {
        var orderCanceledEvent = new OrderCanceledEvent();

        BeanUtils.copyProperties(order, orderCanceledEvent);
        orderCanceledEvent.setStatus(order.getOrderStatus());
        List<OrderItemCanceledEvent> itemEvents = order.getOrderItems().stream()
                .map(itemResponse -> {
                    var itemEvent = new OrderItemCanceledEvent();
                    BeanUtils.copyProperties(itemResponse, itemEvent);
                    itemEvent.setSubtotal(itemResponse.getSubTotal());
                    return itemEvent;
                })
                .toList();

        orderCanceledEvent.setItems(itemEvents);

        log.info("Publicando evento de ordem cancelada {}", order.getId());

        gateway.publishEvent(orderCanceledEvent);
    }
}