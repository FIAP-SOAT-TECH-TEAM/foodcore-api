package com.soat.fiap.food.core.api.payment.infrastructure.common.source;

import com.soat.fiap.food.core.api.payment.core.application.inputs.OrderCreatedInput;
import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.request.GenerateQrCodeRequest;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.AcquirerOrderResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.AcquirerPaymentsResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.GenerateQrCodeResponse;

import java.time.LocalDateTime;

/**
 *  DataSource para comunicação com o adquirente
 */
public interface AcquirerSource {
    /**
     * Gera um QR Code para pagamento com base na requisição fornecida.
     *
     * @param input Objeto contendo os dados necessários para gerar o QR Code.
     * @param expireIn Data de expiração do qrCode.
     */
    String generateQrCode(OrderCreatedInput input, LocalDateTime expireIn);

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
