package com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Resposta de erro padrão
 */
@Getter @AllArgsConstructor
public class ErrorResponse {
	private final LocalDateTime timestamp;
	private final int status;
	private final String message;
	private final String path;
}
