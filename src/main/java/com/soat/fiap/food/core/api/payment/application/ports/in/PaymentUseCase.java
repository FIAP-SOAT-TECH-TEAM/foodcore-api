package com.soat.fiap.food.core.api.payment.application.ports.in;

import com.soat.fiap.food.core.api.order.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.payment.application.dto.request.MercadoPagoNotificationRequest;

import java.math.BigDecimal;

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
    void notification(MercadoPagoNotificationRequest mercadoPagoNotificationRequest);
//
//    /**
//     * Verifica o status de um pagamento
//     *
//     * @param paymentId ID do pagamento
//     * @return Status atual do pagamento
//     */
//    String checkPaymentStatus(String paymentId);
//
//    /**
//     * Processa uma notificação de pagamento (callback/webhook)
//     *
//     * @param paymentId ID do pagamento
//     * @param status Novo status do pagamento
//     */
//    void processPaymentNotification(String paymentId, String status);
} 