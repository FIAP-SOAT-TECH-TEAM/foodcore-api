package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.client;

import com.soat.fiap.food.core.api.payment.core.application.inputs.OrderCreatedInput;
import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.request.GenerateQrCodeRequest;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.AcquirerOrderResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.AcquirerPaymentsResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.in.web.api.dto.response.GenerateQrCodeResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.config.MercadoPagoProperties;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.exceptions.MercadoPagoException;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.mapper.request.GenerateQrCodeRequestMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import retrofit2.Response;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.time.temporal.ChronoUnit;

/**
 * Implementação da porta de saída referente a API do mercado pago
 */
@Component
@Slf4j
public class MercadoPagoSource implements AcquirerSource {

    private final MercadoPagoClient client;
    private final GenerateQrCodeRequestMapper generateQrCodeRequestMapper;
    private final MercadoPagoProperties properties;
    private static final DateTimeFormatter ISO_OFFSET_DATE_TIME_MILLIS_FIXED =
            new DateTimeFormatterBuilder()
                    .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
                    .appendLiteral('.')
                    .appendValue(ChronoField.MILLI_OF_SECOND, 3)
                    .appendOffsetId()
                    .toFormatter();

    public MercadoPagoSource(
            MercadoPagoClient client, GenerateQrCodeRequestMapper generateQrCodeRequestMapper,
            MercadoPagoProperties mercadoPagoProperties
    ) {
        this.client = client;
        this.generateQrCodeRequestMapper = generateQrCodeRequestMapper;
        this.properties = mercadoPagoProperties;
    }

    @Override
    public String generateQrCode(OrderCreatedInput input, LocalDateTime expireIn) {
        try {
            var request = generateQrCodeRequestMapper.toRequest(input);

            request.setNotification_url(properties.getNotificationUrl());

            var laPazExpirationDate = expireIn.atZone(ZoneId.of("America/Sao_Paulo"))
                    .withZoneSameInstant(ZoneId.of("America/La_Paz"))
                    .truncatedTo(ChronoUnit.MILLIS)
                    .format(ISO_OFFSET_DATE_TIME_MILLIS_FIXED);
            request.setExpiration_date(laPazExpirationDate);

            Response<GenerateQrCodeResponse> response = client.generateQrCode(
                    properties.getUserId(),
                    properties.getPosId(),
                    request
            ).execute();
            if (response.isSuccessful() && response.body() != null) {
                return response.body().getQr_data();
            } else {
                log.warn("Erro ao contatar API do mercado pago para gerar QrCode");
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
            log.error("Erro inesperado ao contatar API do mercado pago para gerar QrCode");
            throw new MercadoPagoException("Erro inesperado ao chamar API Mercado Pago", e, 500);
        }
    }

    @Override
    public AcquirerPaymentsResponse getAcquirerPayments(String id) {
        try {
            var response = client.getMercadoPagoPayments(id).execute();

            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                log.warn("Erro ao contatar API do mercado pago para obter dados do pagamento");

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
            log.error("Erro inesperado ao contatar API do mercado pago obter dados do pagamento");
            throw new MercadoPagoException("Erro inesperado ao chamar API Mercado Pago", e, 500);
        }

    }

    @Override
    public AcquirerOrderResponse getAcquirerOrder(Long orderId) {
        try {
            var response = client.getMercadoPagoOrder(orderId).execute();

            if (response.isSuccessful() && response.body() != null) {
                return response.body();
            } else {
                log.warn("Erro ao contatar API do mercado pago para obter dados do pedido");

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
            log.error("Erro inesperado ao contatar API do mercado pago para obter dados do pedido");
            throw new MercadoPagoException("Erro inesperado ao chamar API Mercado Pago", e, 500);
        }

    }
}