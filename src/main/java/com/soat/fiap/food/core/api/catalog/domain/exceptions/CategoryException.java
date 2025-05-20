package com.soat.fiap.food.core.api.catalog.domain.exceptions;

import com.soat.fiap.food.core.api.shared.exception.BusinessException;

/**
 * Exceção lançada quando uma regra de negócio é violada na entidade categoria
 */
public class CategoryException extends BusinessException {

    public CategoryException(String message) {
        super(message);
    }

    public CategoryException(String message, Throwable cause) {
        super(message, cause);
    }
}
