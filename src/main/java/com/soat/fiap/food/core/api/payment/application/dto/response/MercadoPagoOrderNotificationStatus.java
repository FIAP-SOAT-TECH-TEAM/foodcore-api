package com.soat.fiap.food.core.api.payment.application.dto.response;

/**
 * DTO para o status de notificação de um pedido no mercado pago
 */
public enum MercadoPagoOrderNotificationStatus {
    OPENED("Aberta"),
    CLOSE("Fechada");

    private final String description;

    MercadoPagoOrderNotificationStatus(String description) {
        this.description = description;
    }

    public static MercadoPagoOrderNotificationStatus fromValue(String value) {

        String normalized = value.trim().toUpperCase().replace("-", "_").replace(" ", "_");

        for (MercadoPagoOrderNotificationStatus method : values()) {
            if (method.name().equalsIgnoreCase(normalized)) {
                return method;
            }
        }

        throw new IllegalArgumentException("MercadoPagoOrderNotificationStatus desconhecido: " + value);
    }
}
