package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.client;

import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.request.GenerateQrCodeRequest;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.GenerateQrCodeResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.AcquirerOrderResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.AcquirerPaymentsResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

/**
 * Cliente de conexão com a API do mercado pago
 */
public interface MercadoPagoClient {

    @POST("/instore/orders/qr/seller/collectors/{user_id}/pos/{pos_id}/qrs")
    Call<GenerateQrCodeResponse> generateQrCode(
            @Path("user_id") String userId,
            @Path("pos_id") String posId,
            @Body GenerateQrCodeRequest request
    );

    @GET("/v1/payments/{payment_id}")
    Call<AcquirerPaymentsResponse> getMercadoPagoPayments(
            @Path("payment_id") String paymentId
    );

    @GET("/merchant_orders/{order_id}")
    Call<AcquirerOrderResponse> getMercadoPagoOrder(
            @Path("order_id") Long orderId
    );

}