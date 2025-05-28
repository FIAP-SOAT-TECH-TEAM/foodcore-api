package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.client;

import com.soat.fiap.food.core.api.payment.domain.ports.out.MercadoPagoPort;
import com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.entities.GenerateQrCodeRequest;
import com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.entities.GenerateQrCodeResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.exceptions.MercadoPagoException;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import org.springframework.stereotype.Component;
import retrofit2.Response;

@Component
public class MercadoPagoAdapter implements MercadoPagoPort {

    private final MercadoPagoClient client;
    private final CustomLogger logger;

    public MercadoPagoAdapter(
            MercadoPagoClient client,
            CustomLogger logger
    ) {
        this.client = client;
        this.logger = logger;
    }

    @Override
    public GenerateQrCodeResponse generateQrCode(GenerateQrCodeRequest request) {
        try {
            Response<GenerateQrCodeResponse> response = client.generateQrCode(request).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                logger.warn("Erro ao contatar API do mercado pago para gerar QrCode");
                String errorBody = response.errorBody() != null ? response.errorBody().string() : "Erro desconhecido";
                throw new MercadoPagoException(
                        "Erro na API Mercado Pago: " + errorBody + " | Status code: " + response.code(),
                        null,
                        response.code()
                );
            }
        } catch (MercadoPagoException e) {
            throw e;
        } catch (Exception e) {
            logger.error("Erro inesperado ao contatar API do mercado pago para gerar QrCode");
            throw new MercadoPagoException("Erro inesperado ao chamar API Mercado Pago", e, 500);
        }
    }
}