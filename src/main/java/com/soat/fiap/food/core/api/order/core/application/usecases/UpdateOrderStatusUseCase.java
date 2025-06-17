package com.soat.fiap.food.core.api.order.core.application.usecases;

import com.soat.fiap.food.core.api.order.core.domain.events.OrderCanceledEvent;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderItemCanceledEvent;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderItemCreatedEvent;
import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderNotFoundException;
import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.request.CreateOrderRequest;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.request.OrderStatusRequest;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.response.OrderResponse;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.response.OrderStatusResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;

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
     */
    public void updateOrderStatus(Long orderId, OrderStatus orderStatus) {
        logger.info("Atualizando status do pedido {} para {}", orderId, orderStatus);

        var order = dataSource.findById(orderId);

        if (order.isEmpty()) {
            throw new OrderNotFoundException("Pedido", orderId);
        }
        else if (order.get().getOrderStatus() == orderStatus) {
            return;
        }

        order.get().setOrderStatus(orderStatus);

        var updatedOrder = dataSource.save(order.get());

        logger.info("Status do pedido {} atualizado para {}", orderId, orderStatus);

        if (orderStatus == OrderStatus.CANCELLED) {
            var orderCanceledEvent = new OrderCanceledEvent();

            BeanUtils.copyProperties(updatedOrder, orderCanceledEvent);
            List<OrderItemCanceledEvent> itemEvents = updatedOrder.getOrderItems().stream()
                    .map(itemResponse -> {
                        var itemEvent = new OrderItemCanceledEvent();
                        BeanUtils.copyProperties(itemResponse, itemEvent);
                        return itemEvent;
                    })
                    .toList();

            orderCanceledEvent.setItems(itemEvents);

            BeanUtils.copyProperties(updatedOrder.getOrderItems(), orderCanceledEvent.getItems());

            logger.info("Publicando evento de ordem cancelada {}", orderId);

            eventPublisher.publishEvent(orderCanceledEvent);
        }
    }

    /**
     * Atualiza o status de um pedido
     *
     * @param orderId ID do pedido
     * @param orderStatusRequest novo status do pedido
     */
    public OrderStatusResponse updateOrderStatus(Long orderId, OrderStatusRequest orderStatusRequest) {
        logger.info("Atualizando status do pedido {} para {}", orderId, orderStatusRequest.getOrderStatus());

        var order = dataSource.findById(orderId);
        var newStatus = orderStatusRequest.getOrderStatus();

        if (order.isEmpty()) {
            throw new OrderNotFoundException("Pedido", orderId);
        }
        else if (order.get().getOrderStatus() == newStatus) {
            return orderStatusResponseMapper.toResponse(order.get());
        }

        order.get().setOrderStatus(newStatus);

        orderPaymentService.validateOrderPayment(order.get());

        var updatedOrder = dataSource.save(order.get());
        var orderStatusToResponse = orderStatusResponseMapper.toResponse(updatedOrder);

        logger.info("Status do pedido {} atualizado para {}", orderId, newStatus);

        if (orderStatusRequest.getOrderStatus() == OrderStatus.CANCELLED) {
            var orderCanceledEvent = new OrderCanceledEvent();

            BeanUtils.copyProperties(updatedOrder, orderCanceledEvent);
            List<OrderItemCanceledEvent> itemEvents = updatedOrder.getOrderItems().stream()
                    .map(itemResponse -> {
                        var itemEvent = new OrderItemCanceledEvent();
                        BeanUtils.copyProperties(itemResponse, itemEvent);
                        return itemEvent;
                    })
                    .toList();

            orderCanceledEvent.setItems(itemEvents);

            BeanUtils.copyProperties(updatedOrder.getOrderItems(), orderCanceledEvent.getItems());

            logger.info("Publicando evento de ordem cancelada {}", orderId);

            eventPublisher.publishEvent(orderCanceledEvent);
        }

        return orderStatusToResponse;
    }
}
