package com.soat.fiap.food.core.api.order.domain.service;

import com.soat.fiap.food.core.api.order.domain.exceptions.OrderException;
import com.soat.fiap.food.core.api.order.domain.model.Order;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.payment.domain.exceptions.PaymentNotFoundException;
import com.soat.fiap.food.core.api.payment.domain.ports.out.PaymentRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

/**
 * Serviço de domínio responsável pela validação do pagamento de um pedido.
 */
@Data
@Service
public class OrderPaymentService {

    private final PaymentRepository paymentRepository;

    public OrderPaymentService(PaymentRepository paymentRepository) {
        this.paymentRepository = paymentRepository;
    }

    public void validateOrderPayment(Order order) {

        var payment = paymentRepository.findByOrderId(order.getId());

        if (payment.isEmpty()) {
            throw new PaymentNotFoundException("O pagamento do pedido não existe");
        }
        else if (order.getOrderStatus() != OrderStatus.PREPARING && order.getOrderStatus() != OrderStatus.READY) {
            throw new OrderException("Só é possível alterar diretamente o status de ordens em preparo ou prontas para entrega");
        }
    }
}