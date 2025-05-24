package com.soat.fiap.food.core.api.catalog.domain.exceptions;

import com.soat.fiap.food.core.api.shared.exception.ResourceConflictException;

/**
 * Exceção lançada quando um conflito referente a entidade categoria é encontrada
 */
public class CategoryConflictException extends ResourceConflictException {

    public CategoryConflictException(String message) {
        super(message);
    }
}
