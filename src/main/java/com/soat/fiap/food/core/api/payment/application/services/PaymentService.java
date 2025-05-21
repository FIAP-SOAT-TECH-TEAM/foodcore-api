package com.soat.fiap.food.core.api.payment.application.services;

import com.soat.fiap.food.core.api.payment.application.ports.in.PaymentUseCase;
import com.soat.fiap.food.core.api.payment.application.ports.out.PaymentRepository;
import com.soat.fiap.food.core.api.payment.domain.events.PaymentApprovedEvent;
import com.soat.fiap.food.core.api.payment.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.api.order.domain.vo.OrderPaymentStatus;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

/**
 * Implementação do caso de uso de pagamento
 */
@Service
@Slf4j
public class PaymentService implements PaymentUseCase {
    
    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher eventPublisher;
    
    public PaymentService(
            PaymentRepository paymentRepository,
            ApplicationEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }
    
    @Override
    @Transactional
    public String initializePayment(Long orderId, BigDecimal totalAmount) {
        log.info("Inicializando pagamento para o pedido {} no valor de {}", orderId, totalAmount);
        
        var existingPayment = paymentRepository.findByOrderId(orderId);
        if (existingPayment.isPresent()) {
            log.info("Pagamento já existe para o pedido {}: {}", orderId, existingPayment.get().getId());
            return existingPayment.get().getExternalId();
        }
        
        var payment = Payment.builder()
                .orderId(orderId)
                .amount(totalAmount)
                .method(PaymentMethod.PIX) // Assumindo PIX como padrão por enquanto
                .status(OrderPaymentStatus.PENDING)
                .externalId("PAY-" + UUID.randomUUID().toString())
                .createdAt(LocalDateTime.now())
                .build();
        
        // TODO: integração com gateway de pagamento (ex: Mercado Pago)
        // Implementar lógica para gerar QR Code
        payment.setQrCodeUrl("https://api.exemplo.com/qrcode/" + payment.getExternalId());
        payment.setQrCodeData("00020126580014BR.GOV.BCB.PIX0136a629534e-7693-46c9-8551-b9b94b54fec10" + 
                totalAmount.toString().replace(".", ""));
        
        var savedPayment = paymentRepository.save(payment);
        log.info("Pagamento inicializado com ID: {}, externalId: {}", 
                savedPayment.getId(), savedPayment.getExternalId());
        
        return savedPayment.getExternalId();
    }
    
    @Override
    @Transactional(readOnly = true)
    public String checkPaymentStatus(String paymentId) {
        log.info("Verificando status do pagamento: {}", paymentId);
        
        var payment = paymentRepository.findByExternalId(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado: " + paymentId));
        
        return payment.getStatus().name();
    }
    
    @Override
    @Transactional
    public void processPaymentNotification(String paymentId, String status) {
        log.info("Processando notificação de pagamento: {} com status: {}", paymentId, status);
        
        var payment = paymentRepository.findByExternalId(paymentId)
                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado: " + paymentId));
        
        OrderPaymentStatus newStatus;
        try {
            newStatus = OrderPaymentStatus.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            log.error("Status de pagamento inválido: {}", status);
            throw new IllegalArgumentException("Status de pagamento inválido: " + status);
        }
        
        payment.updateStatus(newStatus);
        paymentRepository.save(payment);

        if (newStatus == OrderPaymentStatus.APPROVED) {
            log.info("Pagamento {} aprovado! Publicando evento.", paymentId);
            eventPublisher.publishEvent(
                PaymentApprovedEvent.of(
                    payment.getId(),
                    payment.getOrderId(),
                    payment.getAmount(),
                    payment.getMethod().name()
                )
            );
        }
    }
} 