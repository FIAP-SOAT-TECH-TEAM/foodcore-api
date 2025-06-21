package com.soat.fiap.food.core.api.order.core.application.usecases;

import com.soat.fiap.food.core.api.order.core.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderItemCreatedEvent;
import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.EventPublisherGateway;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Caso de uso: publicar o evento {@link OrderCreatedEvent}
 */
public class PublishCreateOrderEventUseCase {

    /**
     * Publica o evento {@link OrderCreatedEvent}, incluindo os itens do pedido
     * como {@link OrderItemCreatedEvent}.
     *
     * @param order O pedido criado que será convertido em evento.
     * @param gateway O gateway responsável por publicar o evento.
     */
    public static void publishCreateOrderEvent(Order order, EventPublisherGateway gateway) {
        var orderCreatedEvent = new OrderCreatedEvent();

        BeanUtils.copyProperties(order, orderCreatedEvent);

        List<OrderItemCreatedEvent> itemEvents = order.getOrderItems().stream()
                .map(itemResponse -> {
                    OrderItemCreatedEvent itemEvent = new OrderItemCreatedEvent();
                    BeanUtils.copyProperties(itemResponse, itemEvent);
                    return itemEvent;
                })
                .toList();

        orderCreatedEvent.setItems(itemEvents);

        gateway.publishEvent(orderCreatedEvent);
    }
}