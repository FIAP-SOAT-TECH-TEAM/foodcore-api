package com.soat.fiap.food.core.api.payment.domain.ports.out;

import com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.entities.GenerateQrCodeRequest;
import com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.entities.GenerateQrCodeResponse;

/**
 * Porta de saída para API do mercado pago
 */
public interface MercadoPagoPort {
    GenerateQrCodeResponse generateQrCode(GenerateQrCodeRequest request);
}
