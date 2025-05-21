package com.soat.fiap.food.core.api.catalog.domain.exceptions;

import com.soat.fiap.food.core.api.shared.exception.BusinessException;

/**
 * Exceção lançada quando uma regra de negócio é violada na entidade catalogo
 */
public class CatalogException extends BusinessException {

    public CatalogException(String message) {
        super(message);
    }

    public CatalogException(String message, Throwable cause) {
        super(message, cause);
    }
}
