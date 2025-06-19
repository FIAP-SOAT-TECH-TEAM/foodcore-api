package com.soat.fiap.food.core.api.payment.core.interfaceadapters.controller.web.api;

import com.soat.fiap.food.core.api.payment.core.application.usecases.GetAcquirerOrderUseCase;
import com.soat.fiap.food.core.api.payment.core.application.usecases.GetLatestPaymentByOrderIdUseCase;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.AcquirerGateway;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.presenter.web.api.PaymentPresenter;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.AcquirerOrderResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.QrCodeResponse;

public class GetAcquirerOrderController {

    public static AcquirerOrderResponse getAcquirerOrder(
            Long orderId,
            AcquirerSource acquirerSource) {

        var acquirerGateway = new AcquirerGateway(acquirerSource);
        var order = GetAcquirerOrderUseCase.getAcquirerOrder(orderId, acquirerGateway);

        return PaymentPresenter.toQrCodeResponse(payment);
    }
}