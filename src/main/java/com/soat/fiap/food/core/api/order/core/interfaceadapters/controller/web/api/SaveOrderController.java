package com.soat.fiap.food.core.api.order.core.interfaceadapters.controller.web.api;

import com.soat.fiap.food.core.api.order.core.application.inputs.mappers.CreateOrderMapper;
import com.soat.fiap.food.core.api.order.core.application.usecases.CreateOrderUseCase;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderItemCreatedEvent;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.gateways.OrderGateway;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.presenter.web.api.OrderPresenter;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.DataSource;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.request.CreateOrderRequest;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.response.OrderResponse;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.EventPublisherGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * Controller: Salvar pedido.
 *
 */
@Slf4j
public class SaveOrderController {


    /**
     * Salva um pedido.
     *
     * @param createOrderRequest Pedido a ser salvo
     * @param dataSource Origem de dados para o gateway
     * @param eventPublisherSource  Origem de publicação de eventos
     * @return Pedido salvo com identificadores atualizados
     */
    public static OrderResponse saveOrder (CreateOrderRequest createOrderRequest, DataSource dataSource, EventPublisherSource eventPublisherSource) {
        var gateway = new OrderGateway(dataSource);
        var eventPublisherGateway = new EventPublisherGateway(eventPublisherSource);

        var orderInput = CreateOrderMapper.toInput(createOrderRequest);

        var order = CreateOrderUseCase.createOrder(orderInput, gateway);

        var savedOrder = gateway.save(order);

        var saveOrderToResponse = OrderPresenter.toOrderResponse(savedOrder);

        var orderCreatedEvent = new OrderCreatedEvent();

        BeanUtils.copyProperties(saveOrderToResponse, orderCreatedEvent);
        List<OrderItemCreatedEvent> itemEvents = saveOrderToResponse.getItems().stream()
                .map(itemResponse -> {
                    OrderItemCreatedEvent itemEvent = new OrderItemCreatedEvent();
                    BeanUtils.copyProperties(itemResponse, itemEvent);
                    return itemEvent;
                })
                .toList();

        orderCreatedEvent.setItems(itemEvents);

        BeanUtils.copyProperties(saveOrderToResponse.getItems(), orderCreatedEvent.getItems());

        eventPublisherGateway.publishEvent(orderCreatedEvent);

        log.info("Pedido {} criado com sucesso. Total: {}", savedOrder.getId(), savedOrder.getAmount());

        return saveOrderToResponse;
    }
}
