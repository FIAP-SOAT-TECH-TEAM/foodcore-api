package com.soat.fiap.food.core.api.order.domain.events;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Evento de domínio emitido quando um pedido é cancelado
 */
@Data
public class OrderItemCanceledEvent {

    private Long id;

    private Long productId;

    private String name;

    private Integer quantity;

    private BigDecimal unitPrice;

    private BigDecimal subtotal;

    private String observations;
}
