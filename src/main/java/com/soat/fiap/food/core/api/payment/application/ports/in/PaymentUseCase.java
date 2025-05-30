package com.soat.fiap.food.core.api.payment.application.ports.in;

import com.soat.fiap.food.core.api.order.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.payment.application.dto.request.MercadoPagoNotificationRequest;
import com.soat.fiap.food.core.api.payment.application.dto.response.PaymentStatusResponse;
import com.soat.fiap.food.core.api.payment.application.dto.response.QrCodeResponse;

/**
 * Interface do caso de uso de pagamento
 */
public interface PaymentUseCase {
    
    /**
     * Inicializa um pagamento para um pedido
     *
     * @param orderCreatedEvent evento de criação de pedido
     */
    void initializePayment(OrderCreatedEvent orderCreatedEvent);

    /**
     * Gerencia notificação de pagamento mercado pago
     *
     * @param mercadoPagoNotificationRequest notificação de pagamento mercado pago
     */
    void processPaymentNotification(MercadoPagoNotificationRequest mercadoPagoNotificationRequest);

    /**
     * Retorna o status de pagamento de um pedido
     *
     * @param orderId id do pedido
     */
    PaymentStatusResponse getOrderPaymentStatus(Long orderId);

    /**
     * Processa pagamentos não aprovados expirados
     *
     */
    void processExpiredPayments();

    /**
     * Retorna o qr code de pagamento de um dado pedido
     *
     * @param orderId id do pedido
     */
     QrCodeResponse getOrderPaymentQrCode(Long orderId);
} 