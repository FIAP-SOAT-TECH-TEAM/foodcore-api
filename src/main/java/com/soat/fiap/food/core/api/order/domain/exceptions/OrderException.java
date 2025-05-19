package com.soat.fiap.food.core.api.order.domain.exceptions;

import com.soat.fiap.food.core.api.shared.exception.BusinessException;

public class OrderException extends BusinessException {

    public OrderException(String message) {
        super(message);
    }

    public OrderException(String message, Throwable cause) {
        super(message, cause);
    }
}
