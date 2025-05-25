package com.soat.fiap.food.core.api.catalog.domain.exceptions;

import com.soat.fiap.food.core.api.shared.exception.ResourceConflictException;
import com.soat.fiap.food.core.api.shared.exception.ResourceNotFoundException;

/**
 * Exceção lançada quando uma categoria não é encontrada
 */
public class CategoryNotFoundException extends ResourceNotFoundException {

    public CategoryNotFoundException(String message) {
        super(message);
    }
    public CategoryNotFoundException(String message, Long id) {
        super(message, id);
    }
}
