package com.soat.fiap.food.core.api.payment.application.ports.out;

import com.soat.fiap.food.core.api.payment.application.dto.request.GenerateQrCodeRequest;
import com.soat.fiap.food.core.api.payment.application.dto.response.GenerateQrCodeResponse;
import com.soat.fiap.food.core.api.payment.application.dto.response.MercadoPagoOrderResponse;
import com.soat.fiap.food.core.api.payment.application.dto.response.MercadoPagoPaymentsResponse;

/**
 * Porta de saída para API do mercado pago
 */
public interface MercadoPagoPort {
    /**
     * Gera um QR Code para pagamento com base na requisição fornecida.
     *
     * @param request Objeto contendo os dados necessários para gerar o QR Code.
     */
    GenerateQrCodeResponse generateQrCode(GenerateQrCodeRequest request);

    /**
     * Consulta os pagamentos do Mercado Pago pelo ID informado.
     *
     * @param id Identificador do pagamento.
     */
    MercadoPagoPaymentsResponse getMercadoPagoPayments(String id);

    /**
     * Consulta um pedido (order) do Mercado Pago pelo seu ID.
     *
     * @param orderId Identificador do pedido.
     */
    MercadoPagoOrderResponse getMercadoPagoOrder(Long orderId);
}
