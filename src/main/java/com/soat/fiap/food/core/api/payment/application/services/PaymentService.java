package com.soat.fiap.food.core.api.payment.application.services;

import com.soat.fiap.food.core.api.order.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.payment.application.dto.request.MercadoPagoNotificationRequest;
import com.soat.fiap.food.core.api.payment.application.mapper.request.GenerateQrCodeRequestMapper;
import com.soat.fiap.food.core.api.payment.application.ports.in.PaymentUseCase;
import com.soat.fiap.food.core.api.payment.application.ports.out.MercadoPagoPort;
import com.soat.fiap.food.core.api.payment.domain.events.PaymentInitializationErrorEvent;
import com.soat.fiap.food.core.api.payment.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.domain.ports.out.PaymentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Implementação do caso de uso de pagamento
 */
@Service
@Slf4j
public class PaymentService implements PaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final MercadoPagoPort mercadoPagoPort;
    private final GenerateQrCodeRequestMapper generateQrCodeRequestMapper;
    private final ApplicationEventPublisher eventPublisher;

    public PaymentService(
            PaymentRepository paymentRepository,
            MercadoPagoPort mercadoPagoPort,
            GenerateQrCodeRequestMapper generateQrCodeRequestMapper,
            ApplicationEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.mercadoPagoPort = mercadoPagoPort;
        this.eventPublisher = eventPublisher;
        this.generateQrCodeRequestMapper = generateQrCodeRequestMapper;
    }

    @Override
    @Transactional
    public void initializePayment(OrderCreatedEvent event) {

        try {
            log.info("Inicializando pagamento para o pedido {} no valor de {}", event.getId(), event.getTotalAmount());

            if (paymentRepository.existsByOrderId(event.getId())) {
                log.info("Pagamento já existe para o pedido {}", event.getId());
                return;
            }

            var generateQrCodeBody = generateQrCodeRequestMapper.toRequest(event);
            var payment = new Payment(
                    event.getUserId(),
                    event.getId(),
                    generateQrCodeBody.getTotal_amount()
            );

            var generateQrCodeResponse = mercadoPagoPort.generateQrCode(generateQrCodeBody);

            payment.setQrCode(generateQrCodeResponse.getQr_data());
            payment.setTid(generateQrCodeResponse.getIn_store_order_id());

            var savedPayment = paymentRepository.save(payment);

            log.info("Pagamento inicializado com ID: {}, qrCode: {}, pedido: {}",
                    savedPayment.getId(), savedPayment.getQrCode(), savedPayment.getOrderId());

        }
        catch (Exception ex) {

            log.warn("Erro na inicialização do pagamento. Pedido: {}, Causa: {}", event.getId(), ex.getMessage());

            var paymentInitializationErrorEvent = new PaymentInitializationErrorEvent(event.getId(), ex.getMessage());
            eventPublisher.publishEvent(paymentInitializationErrorEvent);

            throw  ex;
        }
    }

    @Override
    @Transactional
    public void notification(MercadoPagoNotificationRequest mercadoPagoNotificationRequest) {

        var a = 2;

    }

//
//    @Override
//    @Transactional(readOnly = true)
//    public String checkPaymentStatus(String paymentId) {
//        log.info("Verificando status do pagamento: {}", paymentId);
//
//        var payment = paymentRepository.findByExternalId(paymentId)
//                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado: " + paymentId));
//
//        return payment.getStatus().name();
//    }
//
//    @Override
//    @Transactional
//    public void processPaymentNotification(String paymentId, String status) {
//        log.info("Processando notificação de pagamento: {} com status: {}", paymentId, status);
//
//        var payment = paymentRepository.findByExternalId(paymentId)
//                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado: " + paymentId));
//
//        OrderPaymentStatus newStatus;
//        try {
//            newStatus = OrderPaymentStatus.valueOf(status.toUpperCase());
//        } catch (IllegalArgumentException e) {
//            log.error("Status de pagamento inválido: {}", status);
//            throw new IllegalArgumentException("Status de pagamento inválido: " + status);
//        }
//
//        payment.updateStatus(newStatus);
//        paymentRepository.save(payment);
//
//        if (newStatus == OrderPaymentStatus.APPROVED) {
//            log.info("Pagamento {} aprovado! Publicando evento.", paymentId);
//            eventPublisher.publishEvent(
//                PaymentApprovedEvent.of(
//                    payment.getId(),
//                    payment.getOrderId(),
//                    payment.getAmount(),
//                    payment.getMethod().name()
//                )
//            );
//        }
//    }
}