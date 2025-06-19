package com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response;

/**
 * DTO para o status de notificação de um pedido no adquirente
 */
public enum AcquirerOrderNotificationStatus {
    OPENED("Aberta"),
    CLOSE("Fechada");

    private final String description;

    AcquirerOrderNotificationStatus(String description) {
        this.description = description;
    }

    public static AcquirerOrderNotificationStatus fromValue(String value) {

        String normalized = value.trim().toUpperCase().replace("-", "_").replace(" ", "_");

        for (AcquirerOrderNotificationStatus method : values()) {
            if (method.name().equalsIgnoreCase(normalized)) {
                return method;
            }
        }

        throw new IllegalArgumentException("AcquirerOrderNotificationStatus desconhecido: " + value);
    }
}
