package com.soat.fiap.food.core.api.order.domain.events;

import com.soat.fiap.food.core.api.order.application.dto.response.OrderItemResponse;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * Evento de domínio emitido quando um pedido é criado
 */
@Data
public class OrderCreatedEvent {

    private Long id;

    private String orderNumber;

    private OrderStatus status;

    private String statusDescription;

    private Long userId;

    private BigDecimal totalAmount;

    private List<OrderItemCreatedEvent> items;

} 