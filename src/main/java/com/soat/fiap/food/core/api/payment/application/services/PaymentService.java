package com.soat.fiap.food.core.api.payment.application.services;

import com.soat.fiap.food.core.api.payment.application.ports.in.PaymentUseCase;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

import lombok.extern.slf4j.Slf4j;

/**
 * Implementação do caso de uso de pagamento
 */
@Service
@Slf4j
public class PaymentService implements PaymentUseCase {
    
    private final ApplicationEventPublisher eventPublisher;
    
    public PaymentService(ApplicationEventPublisher eventPublisher) {
        this.eventPublisher = eventPublisher;
    }
    
    @Override
    @Transactional
    public String initializePayment(Long orderId, BigDecimal totalAmount) {
        log.info("Inicializando pagamento para o pedido {} no valor de {}", orderId, totalAmount);
        
        // TODO: fazer integração com o gateway de pagamento real ou fake
        // e gerado o QR Code para o Mercado Pago por ex.
        
        // Simulação - gera um ID de pagamento fictício
        String paymentId = "PAY-" + orderId + "-" + System.currentTimeMillis();
        
        log.info("Pagamento inicializado com ID: {}", paymentId);
        return paymentId;
    }
    
    @Override
    @Transactional(readOnly = true)
    public String checkPaymentStatus(String paymentId) {
        log.info("Verificando status do pagamento: {}", paymentId);
        
        // TODO: consultar o status do pagamento no gateway
        
        // Simulação - retorna um status fixo para testes
        return "PENDING";
    }
    
    @Override
    @Transactional
    public void processPaymentNotification(String paymentId, String status) {
        log.info("Processando notificação de pagamento: {} com status: {}", paymentId, status);
        
        // TODO: processar a notificação do gateway
        // e publicar um evento de domínio quando o pagamento for confirmado
        
        if ("APPROVED".equals(status)) {
            log.info("Pagamento {} aprovado!", paymentId);
            // Publicar evento de pagamento aprovado
            // eventPublisher.publishEvent(new PaymentApprovedEvent(paymentId));
        }
    }
} 