package com.soat.fiap.food.core.api.payment.application.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * DTO para resposta de pagamento
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponse {
    private Long id;
    private Long orderId;
    private String externalId;
    private BigDecimal amount;
    private String paymentMethod;
    private String status;
    private String qrCodeUrl;
    private String qrCodeData;
    private LocalDateTime createdAt;
    private LocalDateTime processedAt;
} 