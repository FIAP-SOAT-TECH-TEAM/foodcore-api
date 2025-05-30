package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.deserializers;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import com.soat.fiap.food.core.api.payment.application.dto.response.MercadoPagoOrderNotificationStatus;
import com.soat.fiap.food.core.api.payment.application.dto.response.MercadoPagoOrderStatus;

import java.io.IOException;

/**
 * TypeAdapter customizado para MercadoPagoOrderNotificationStatus (enum) para uso com GSON.
 */
public class MercadoPagoOrderNotificationStatusTypeAdapter extends TypeAdapter<MercadoPagoOrderNotificationStatus> {
    @Override
    public void write(JsonWriter out, MercadoPagoOrderNotificationStatus value) throws IOException {
        if (value == null) {
            out.nullValue();
        } else {
            out.value(value.name());
        }
    }

    @Override
    public MercadoPagoOrderNotificationStatus read(JsonReader in) throws IOException {
        String value = in.nextString();
        return MercadoPagoOrderNotificationStatus.fromValue(value);
    }
}
