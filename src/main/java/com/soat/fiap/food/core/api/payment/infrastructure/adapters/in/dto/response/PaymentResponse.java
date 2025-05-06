package com.soat.fiap.food.core.api.payment.infrastructure.adapters.in.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
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