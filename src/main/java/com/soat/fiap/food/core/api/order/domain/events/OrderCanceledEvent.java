package com.soat.fiap.food.core.api.order.domain.events;

import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Evento de domínio emitido quando um pedido é cancelado
 */
@Data
public class OrderCanceledEvent {

    private Long id;

    private OrderStatus status;

    private List<OrderItemCanceledEvent> items;

} 