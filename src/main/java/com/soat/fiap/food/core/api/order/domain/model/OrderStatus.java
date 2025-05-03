package com.soat.fiap.food.core.api.order.domain.model;

import lombok.Getter;

/**
 * Enum que representa os possíveis status de um pedido
 */
@Getter
public enum OrderStatus {
    PENDING("Pendente"),
    PREPARING("Em Preparação"),
    READY("Pronto"),
    COMPLETED("Finalizado"),
    CANCELLED("Cancelado");
    
    private final String description;
    
    OrderStatus(String description) {
        this.description = description;
    }

} 