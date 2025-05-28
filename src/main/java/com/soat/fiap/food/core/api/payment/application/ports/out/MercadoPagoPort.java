package com.soat.fiap.food.core.api.payment.application.ports.out;

import com.soat.fiap.food.core.api.payment.application.dto.request.GenerateQrCodeRequest;
import com.soat.fiap.food.core.api.payment.application.dto.response.GenerateQrCodeResponse;

/**
 * Porta de saída para API do mercado pago
 */
public interface MercadoPagoPort {
    GenerateQrCodeResponse generateQrCode(GenerateQrCodeRequest request);
}
