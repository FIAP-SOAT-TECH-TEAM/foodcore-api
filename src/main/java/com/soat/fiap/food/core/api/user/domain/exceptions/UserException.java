package com.soat.fiap.food.core.api.user.domain.exceptions;

import com.soat.fiap.food.core.api.shared.exception.BusinessException;

/**
 * Exceção lançada quando uma regra de negócio é violada na entidade User
 */
public class UserException extends BusinessException {

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }
}
