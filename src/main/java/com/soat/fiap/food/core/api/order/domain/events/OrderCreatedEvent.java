package com.soat.fiap.food.core.api.order.domain.events;

import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Evento de domínio emitido quando um pedido é criado
 */
@Getter
@AllArgsConstructor
public class OrderCreatedEvent {
    private final Long orderId;
    private final BigDecimal totalAmount;
    private final OrderStatus status;
    private final LocalDateTime createdAt;
    private final Long userId;

    /**
     * Cria um evento de pedido criado
     *
     * @param orderId ID do pedido
     * @param totalAmount Valor total do pedido
     * @param status Status do pedido
     * @param userId ‘ID’ do usuário (pode ser null para pedidos anônimos)
     * @return Nova instância do evento
     */
    public static OrderCreatedEvent of(Long orderId, BigDecimal totalAmount, OrderStatus status, Long userId) {
        return new OrderCreatedEvent(orderId, totalAmount, status, LocalDateTime.now(), userId);
    }
} 