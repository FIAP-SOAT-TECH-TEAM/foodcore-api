package com.soat.fiap.food.core.api.order.core.application.usecases;

import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.payment.domain.exceptions.PaymentNotFoundException;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Garantir que o pagamento do pedido seja válido.
 */
@Slf4j
public class EnsureOrderPaymentIsValidUseCase {

    /**
     * Valida se o pagamento existe para o pedido, exceto quando o status for RECEIVED.
     *
     * @param order Pedido a ser validado
     * @throws PaymentNotFoundException se o pagamento não existir
     */
    public static void ensureOrderPaymentIsValid(Order order) {

        var payment = paymentRepository.findTopByOrderIdOrderByIdDesc(order.getId());

        if (payment.isEmpty() && order.getOrderStatus() != OrderStatus.RECEIVED) {
            throw new PaymentNotFoundException("O pagamento do pedido não existe");
        }
    }
}
