package com.soat.fiap.food.core.api.payment.infrastructure.common.source;

import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.request.GenerateQrCodeRequest;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.AcquirerOrderResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.AcquirerPaymentsResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.GenerateQrCodeResponse;

/**
 *  DataSource para comunicação com o adquirente
 */
public interface AcquirerSource {
    /**
     * Gera um QR Code para pagamento com base na requisição fornecida.
     *
     * @param request Objeto contendo os dados necessários para gerar o QR Code.
     */
    GenerateQrCodeResponse generateQrCode(GenerateQrCodeRequest request);

    /**
     * Consulta os pagamentos do adquirente pelo ID informado.
     *
     * @param id Identificador do pagamento.
     */
    AcquirerPaymentsResponse getAcquirerPayments(String id);

    /**
     * Consulta um pedido (order) do adquirente pelo seu ID.
     *
     * @param orderId Identificador do pedido.
     */
    AcquirerOrderResponse getAcquirerOrder(Long orderId);
}
