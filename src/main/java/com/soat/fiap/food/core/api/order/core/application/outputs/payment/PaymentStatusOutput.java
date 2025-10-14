package com.soat.fiap.food.core.api.order.core.application.outputs.payment;

/**
 * Representa o status de um pagamento (Application Layer) associado a um pedido,
 * contendo o identificador do pedido e o status atual do pagamento.
 *
 * @param orderId
 *            Identificador único do pedido.
 * @param status
 *            Status atual do pagamento.
 */
public record PaymentStatusOutput(Long orderId, StatusEntity status) {
}
