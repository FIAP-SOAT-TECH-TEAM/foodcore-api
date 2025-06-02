package com.soat.fiap.food.core.api.order.domain.exceptions;

import com.soat.fiap.food.core.api.shared.exception.BusinessException;

/**
 * Exceção lançada quando uma regra de negócio é violada na entidade item do pedido
 */
public class OrderItemException extends BusinessException {

    public OrderItemException(String message) {
        super(message);
    }

    public OrderItemException(String message, Throwable cause) {
        super(message, cause);
    }
}
