package com.soat.fiap.food.core.api.payment.core.interfaceadapters.controller.web.api;

import com.soat.fiap.food.core.api.payment.core.application.usecases.GetLatestPaymentByOrderIdUseCase;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.presenter.web.api.PaymentPresenter;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.QrCodeResponse;

/**
 * Controller: Obter QR Code de pagamento de um pedido.
 */
public class GetOrderPaymentQrCodeController {

    /**
     * Obtém o QR Code de pagamento do pedido pelo ID do pedido.
     *
     * @param orderId           ID do pedido.
     * @param paymentDataSource Origem de dados para o gateway de pagamento.
     * @return Objeto {@link QrCodeResponse} com o QR Code e dados relacionados ao pagamento.
     */
    public static QrCodeResponse getOrderPaymentQrCode(
            Long orderId,
            PaymentDataSource paymentDataSource) {

        var paymentGateway = new PaymentGateway(paymentDataSource);
        var payment = GetLatestPaymentByOrderIdUseCase.getLatestPaymentByOrderId(orderId, paymentGateway);

        return PaymentPresenter.toQrCodeResponse(payment);
    }
}