package com.soat.fiap.food.core.api.order.domain.exceptions;

import com.soat.fiap.food.core.api.shared.exception.ResourceNotFoundException;

/**
 * Exceção lançada quando uma ordem não é encontrada
 */
public class OrderNotFoundException extends ResourceNotFoundException {

    public OrderNotFoundException(String message) {
        super(message);
    }
    public OrderNotFoundException(String message, Long id) {
        super(message, id);
    }
}
