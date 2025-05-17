package com.soat.fiap.food.core.api.order.domain.vo;

import lombok.Getter;

/**
 * Enum que representa os possíveis status de um pagamento
 */
@Getter
public enum OrderPaymentStatus {
    PENDING("Pendente"),
    APPROVED("Aprovado"),
    REJECTED("Rejeitado"),
    CANCELLED("Cancelado");
    
    private final String description;
    
    OrderPaymentStatus(String description) {
        this.description = description;
    }

} 