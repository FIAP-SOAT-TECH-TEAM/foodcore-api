package com.soat.fiap.food.core.api.payment.application.dto.response;

/**
 * DTO para o status de um pedido no mercado pago
 */
public enum MercadoPagoOrderStatus {
    PAID("Paga"),
    PAYMENT_REQUIRED("Pagamento requerido");

    private final String description;

    MercadoPagoOrderStatus(String description) {
        this.description = description;
    }

    public static MercadoPagoOrderStatus fromValue(String value) {

        String normalized = value.trim().toUpperCase().replace("-", "_").replace(" ", "_");

        for (MercadoPagoOrderStatus method : values()) {
            if (method.name().equalsIgnoreCase(normalized)) {
                return method;
            }
        }

        throw new IllegalArgumentException("MercadoPagoOrderStatus desconhecido: " + value);
    }
}
