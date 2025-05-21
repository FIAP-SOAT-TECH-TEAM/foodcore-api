package com.soat.fiap.food.core.api.payment.domain.vo;

import lombok.Getter;

/**
 * Enum que representa os métodos de pagamento disponíveis
 */
@Getter
public enum PaymentMethod {
    CREDIT_CARD("Cartão de Crédito"),
    DEBIT_CARD("Cartão de Débito"),
    PIX("PIX"),
    CASH("Dinheiro");
    
    private final String description;
    
    PaymentMethod(String description) {
        this.description = description;
    }

} 