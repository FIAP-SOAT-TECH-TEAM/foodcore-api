package com.soat.fiap.food.core.api.catalog.domain.exceptions;

import com.soat.fiap.food.core.api.shared.exception.ResourceConflictException;

/**
 * Exceção lançada quando um conflito referente a entidade produto é encontrada
 */
public class ProductConflictException extends ResourceConflictException {

    public ProductConflictException(String message) {
        super(message);
    }
}
