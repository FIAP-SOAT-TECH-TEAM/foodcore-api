package com.soat.fiap.food.core.api.payment.core.application.outputs;

import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;

/**
 * Representa um output (Application Layer) simplificado de pagamento retornado
 * pelo adquirente, contendo apenas os dados necessários para a lógica de
 * aplicação.
 *
 * @param tid
 *            Identificador da transação no adquirente.
 * @param status
 *            Status atual do pagamento.
 * @param externalReference
 *            Referência externa associada ao pedido local.
 * @param type
 *            Tipo do método de pagamento utilizado.
 */
public record AcquirerPaymentOutput(String tid, PaymentStatus status, Long externalReference, PaymentMethod type) {
}
