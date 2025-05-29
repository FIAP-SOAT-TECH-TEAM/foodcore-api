package com.soat.fiap.food.core.api.payment.infrastructure.adapters.in.controller;

import com.soat.fiap.food.core.api.payment.application.dto.request.MercadoPagoNotificationRequest;
import com.soat.fiap.food.core.api.payment.application.dto.response.PaymentStatusResponse;
import com.soat.fiap.food.core.api.payment.application.ports.in.PaymentUseCase;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Void> mercadoPagoWebhook(@Valid @RequestBody MercadoPagoNotificationRequest notification) {
        log.info("Recebida notificação do Mercado Pago: ação={}, id interno={}, id externo={}",
                notification.getAction(),
                notification.getId(),
                notification.getData() != null ? notification.getData().getId() : "sem id externo");

        paymentUseCase.notification(notification);

        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Buscar status do pagamento por ID do pedido")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status do pagamento retornado com sucesso",
                    content = @Content(mediaType = "application/json", schema = @Schema(implementation = PaymentStatusResponse.class))),
            @ApiResponse(responseCode = "404", description = "Pagamento não encontrado", content = @Content)
    })
    @GetMapping("/{orderId}/status")
    public ResponseEntity<PaymentStatusResponse> getPaymentStatus(@PathVariable Long orderId) {
        log.info("Recebida requisição para obter status do pagamento para orderId {}", orderId);
        var response = paymentUseCase.getPaymentStatus(orderId);
        return ResponseEntity.ok(response);
    }
}