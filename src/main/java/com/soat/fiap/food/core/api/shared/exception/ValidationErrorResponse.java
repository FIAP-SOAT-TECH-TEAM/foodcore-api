package com.soat.fiap.food.core.api.shared.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Map;

/**
 * Classe que representa uma resposta de erro de validação
 */
@Getter
@Setter
public class ValidationErrorResponse extends ErrorResponse {
    private Map<String, String> errors;
    
    public ValidationErrorResponse(LocalDateTime timestamp, int status, String message, Map<String, String> errors, String path) {
        super(timestamp, status, message, path);
        this.errors = errors;
    }
} 