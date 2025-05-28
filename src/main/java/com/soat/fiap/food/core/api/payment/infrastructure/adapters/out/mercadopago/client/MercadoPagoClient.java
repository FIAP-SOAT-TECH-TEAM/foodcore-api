package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.client;

import com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.entities.GenerateQrCodeRequest;
import com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.entities.GenerateQrCodeResponse;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

/**
 * Cliente de conexão com a API do mercado pago
 */
public interface MercadoPagoClient {

    @Headers({
            "Content-Type: application/json",
            "Authorization: Bearer SEU_TOKEN_AQUI"
    })
    @POST("/instore/orders/qr/seller/collectors/{user_id}/pos/{pos_id}/qrs")
    Call<GenerateQrCodeResponse> generateQrCode(@Body GenerateQrCodeRequest request);
}