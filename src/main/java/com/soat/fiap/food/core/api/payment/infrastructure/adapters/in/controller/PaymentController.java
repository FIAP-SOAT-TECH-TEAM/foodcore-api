//package com.soat.fiap.food.core.api.payment.infrastructure.adapters.in.controller;
//
//import com.soat.fiap.food.core.api.payment.application.ports.in.PaymentUseCase;
//import com.soat.fiap.food.core.api.payment.domain.ports.out.PaymentRepository;
//import com.soat.fiap.food.core.api.payment.domain.model.Payment;
//import com.soat.fiap.food.core.api.payment.application.dto.request.ProcessPaymentRequest;
//import com.soat.fiap.food.core.api.payment.application.dto.response.PaymentResponse;
//import jakarta.validation.Valid;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//
///**
// * Controlador REST para pagamentos
// */
//@RestController
//@RequestMapping("/api/orders/{orderId}/payments")
//@Slf4j
//public class PaymentController {
//
//    private final PaymentUseCase paymentUseCase;
//    private final PaymentRepository paymentRepository;
//
//    public PaymentController(PaymentUseCase paymentUseCase, PaymentRepository paymentRepository) {
//        this.paymentUseCase = paymentUseCase;
//        this.paymentRepository = paymentRepository;
//    }
//
//    /**
//     * Processa o pagamento de um pedido
//     *
//     * @param orderId ID do pedido
//     * @param request DTO com método de pagamento
//     * @return Resposta com informações do pagamento
//     */
//    @PostMapping
//    public ResponseEntity<PaymentResponse> processPayment(
//            @PathVariable Long orderId,
//            @Valid @RequestBody ProcessPaymentRequest request) {
//
//        log.info("Recebida requisição para processar pagamento do pedido {} com método {}",
//                orderId, request.getPaymentMethod());
//
//        Payment payment = paymentRepository.findByOrderId(orderId)
//                .orElseThrow(() -> {
//                    paymentUseCase.initializePayment(orderId, null);
//                    return new IllegalStateException("Pagamento não foi inicializado corretamente para o pedido: " + orderId);
//                });
//
//        PaymentResponse response = mapToResponse(payment);
//
//        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
//    }
//
//    /**
//     * Busca o status do pagamento de um pedido
//     *
//     * @param orderId ID do pedido
//     * @return Resposta com informações do pagamento
//     */
//    @GetMapping
//    public ResponseEntity<PaymentResponse> getPaymentStatus(@PathVariable Long orderId) {
//        log.info("Consultando status do pagamento para o pedido: {}", orderId);
//
//        Payment payment = paymentRepository.findByOrderId(orderId)
//                .orElseThrow(() -> new IllegalArgumentException("Pagamento não encontrado para o pedido: " + orderId));
//
//        return ResponseEntity.ok(mapToResponse(payment));
//    }
//
//    /**
//     * Recebe notificação de pagamento (webhook)
//     *
//     * @param externalId ID externo do pagamento
//     * @param status Novo status do pagamento
//     * @return Resposta vazia com código 200
//     */
//    @PostMapping("/webhook/{externalId}")
//    public ResponseEntity<Void> handlePaymentWebhook(
//            @PathVariable String externalId,
//            @RequestParam("status") String status) {
//
//        log.info("Recebido webhook de pagamento: {} com status: {}", externalId, status);
//
//        paymentUseCase.processPaymentNotification(externalId, status);
//
//        return ResponseEntity.ok().build();
//    }
//
//    /**
//     * Mapeia do modelo de domínio para o DTO de resposta
//     *
//     * @param payment Modelo de domínio
//     * @return DTO de resposta
//     */
//    private PaymentResponse mapToResponse(Payment payment) {
//        return PaymentResponse.builder()
//                .id(payment.getId())
//                .orderId(payment.getOrderId())
//                .externalId(payment.getExternalId())
//                .amount(payment.getAmount())
//                .paymentMethod(payment.getMethod().name())
//                .status(payment.getStatus().name())
//                .qrCodeUrl(payment.getQrCodeUrl())
//                .qrCodeData(payment.getQrCodeData())
//                .createdAt(payment.getCreatedAt())
//                .processedAt(payment.getProcessedAt())
//                .build();
//    }
//}