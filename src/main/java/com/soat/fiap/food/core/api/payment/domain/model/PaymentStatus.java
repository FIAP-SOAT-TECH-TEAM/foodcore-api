package com.soat.fiap.food.core.api.payment.domain.model;

import lombok.Getter;

/**
 * Enum que representa os possíveis status de um pagamento
 */
@Getter
public enum PaymentStatus {
    PENDING("Pendente"),
    PROCESSING("Em Processamento"),
    APPROVED("Aprovado"),
    REJECTED("Rejeitado"),
    CANCELLED("Cancelado");
    
    private final String description;
    
    PaymentStatus(String description) {
        this.description = description;
    }

} 