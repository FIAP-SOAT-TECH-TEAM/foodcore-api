package com.soat.fiap.food.core.api.order.application.services;


import com.soat.fiap.food.core.api.order.application.dto.request.CreateOrderRequest;
import com.soat.fiap.food.core.api.order.application.dto.response.OrderResponse;
import com.soat.fiap.food.core.api.order.application.mapper.request.CreateOrderRequestMapper;
import com.soat.fiap.food.core.api.order.application.mapper.response.OrderResponseMapper;
import com.soat.fiap.food.core.api.order.application.ports.in.OrderUseCase;
import com.soat.fiap.food.core.api.order.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.order.domain.events.OrderItemCreatedEvent;
import com.soat.fiap.food.core.api.order.domain.exceptions.OrderNotFoundException;
import com.soat.fiap.food.core.api.order.domain.ports.out.OrderRepository;
import com.soat.fiap.food.core.api.order.domain.service.OrderDiscountService;
import com.soat.fiap.food.core.api.order.domain.service.OrderProductService;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import org.springframework.beans.BeanUtils;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Implementação do caso de uso de pedidos
 */
@Service
public class OrderService implements OrderUseCase {

    private final OrderRepository orderRepository;

    private final OrderDiscountService orderDiscountService;
    private final OrderProductService orderProductService;

    private final CreateOrderRequestMapper createOrderRequestMapper;
    private final OrderResponseMapper orderResponseMapper;

    private final ApplicationEventPublisher eventPublisher;
    private final CustomLogger logger;

    public OrderService(
            ApplicationEventPublisher eventPublisher,
            OrderRepository orderRepository,
            CreateOrderRequestMapper createOrderRequestMapper,
            OrderDiscountService orderDiscountService,
            OrderProductService orderProductService,
            OrderResponseMapper orderResponseMapper) {
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
        this.createOrderRequestMapper = createOrderRequestMapper;
        this.logger = CustomLogger.getLogger(getClass());
        this.orderDiscountService = orderDiscountService;
        this.orderProductService = orderProductService;
        this.orderResponseMapper = orderResponseMapper;
    }

    @Override
    @Transactional
    public OrderResponse createOrder(CreateOrderRequest createOrderRequest) {

        logger.info("Criando novo pedido para o cliente ID: {}", createOrderRequest.getUserId());

        var order = createOrderRequestMapper.toDomain(createOrderRequest);

        orderProductService.validateOrderItemProduct(order.getOrderItems());

        orderDiscountService.applyDiscount(order);

        var savedOrder = orderRepository.save(order);

        var saveOrderToResponse = orderResponseMapper.toResponse(savedOrder);

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

        eventPublisher.publishEvent(orderCreatedEvent);

        logger.info("Pedido {} criado com sucesso. Total: {}", savedOrder.getId(), savedOrder.getAmount());

        return saveOrderToResponse;
    }


    @Override
    @Transactional
    public void updateOrderStatus(Long orderId, OrderStatus newStatus) {
        logger.info("Atualizando status do pedido {} para {}", orderId, newStatus);

        var order = orderRepository.findById(orderId);

        if (order.isEmpty()) {
            throw new OrderNotFoundException("Ordem", orderId);
        }

        order.get().setOrderStatus(newStatus);

        var updatedOrder = orderRepository.save(order.get());

        logger.info("Status do pedido {} atualizado para {}", orderId, newStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderResponse> getActiveOrdersSorted() {
        logger.info("Buscando pedidos ativos ordenados por prioridade e data de criação.");

        var activeOrders = orderRepository.findActiveOrdersSorted();

        return activeOrders.stream()
                .map(orderResponseMapper::toResponse)
                .toList();
    }
} 