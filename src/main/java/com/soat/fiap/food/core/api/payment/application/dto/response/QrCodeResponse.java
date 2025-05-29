package com.soat.fiap.food.core.api.payment.application.dto.response;

import com.soat.fiap.food.core.api.payment.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.api.payment.domain.vo.QrCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta de obtenção do qrcode de um pedido
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QrCodeResponse {
    @Schema(description = "ID do pedido")
    private String orderId;
    @Schema(description = "Código QR do pedido")
    private QrCode qrCode;
} 