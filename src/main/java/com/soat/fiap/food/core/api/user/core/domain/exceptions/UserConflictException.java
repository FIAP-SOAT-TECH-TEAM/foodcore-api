package com.soat.fiap.food.core.api.user.core.domain.exceptions;

import com.soat.fiap.food.core.api.shared.core.domain.exceptions.ResourceConflictException;

/**
 * Exceção lançada quando um conflito referente a entidade usuário é encontrada
 */
public class UserConflictException extends ResourceConflictException {

	public UserConflictException(String message) {
		super(message);
	}

	public UserConflictException(String resourceName, String fieldName, Object fieldValue) {
		super(resourceName, fieldName, fieldValue);
	}
}
