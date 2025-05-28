package com.soat.fiap.food.core.api.payment.infrastructure.adapters.in.controller;

import com.soat.fiap.food.core.api.payment.application.dto.request.MercadoPagoNotificationRequest;
import com.soat.fiap.food.core.api.payment.application.ports.in.PaymentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para pagamentos
 */
@RestController
@RequestMapping("/payments")
@Slf4j
public class PaymentController {

    private final PaymentUseCase paymentUseCase;

    public PaymentController(PaymentUseCase paymentUseCase) {
        this.paymentUseCase = paymentUseCase;
    }

    /**
     * Recebe notificações de pagamento do Mercado Pago
     *
     * @param notification corpo da notificação
     * @return HTTP 200 se a notificação for processada com sucesso
     */
    @Operation(summary = "Webhook do Mercado Pago", description = "Recebe notificações de eventos de pagamento do Mercado Pago")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Notificação processada com sucesso"),
            @ApiResponse(responseCode = "400", description = "Notificação malformada")
    })
    @PostMapping("/webhook")
    public ResponseEntity<Void> mercadoPagoWebhook(@RequestBody MercadoPagoNotificationRequest notification) {
        log.info("Recebida notificação do Mercado Pago: ação={}, id interno={}, id externo={}",
                notification.getAction(),
                notification.getId(),
                notification.getData() != null ? notification.getData().getId() : "sem id externo");

        paymentUseCase.notification(notification);

        return ResponseEntity.ok().build();
    }
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
}