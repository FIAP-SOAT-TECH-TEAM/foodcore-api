package com.soat.fiap.food.core.api.shared.exception;

/**
 * Exceção lançada quando um recurso não é encontrado
 */
public class ResourceNotFoundException extends RuntimeException {
    
    public ResourceNotFoundException(String message) {
        super(message);
    }
    
    public ResourceNotFoundException(String resourceName, Long id) {
        super(String.format("%s não encontrado com id: %d", resourceName, id));
    }
    
    public ResourceNotFoundException(String resourceName, String identifier, String value) {
        super(String.format("%s não encontrado com %s: %s", resourceName, identifier, value));
    }
} 