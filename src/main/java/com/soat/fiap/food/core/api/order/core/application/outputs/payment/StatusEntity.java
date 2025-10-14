package com.soat.fiap.food.core.api.order.core.application.outputs.payment;

import lombok.Getter;

/**
 * Representa o ENUM de status de um pagamento (Application Layer).
 */
@Getter
public enum StatusEntity {
	PENDING("Pendente"),

	APPROVED("Aprovado"),

	REJECTED("Rejeitado"),

	CANCELLED("Cancelado");

	private final String description;

	StatusEntity(String description) {
		this.description = description;
	}
}
