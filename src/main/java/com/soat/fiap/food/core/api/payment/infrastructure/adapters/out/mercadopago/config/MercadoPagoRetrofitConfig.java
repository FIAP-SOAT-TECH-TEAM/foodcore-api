package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.config;

import com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.client.MercadoPagoClient;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class MercadoPagoRetrofitConfig {

    @Bean
    public MercadoPagoClient mercadoPagoClient(MercadoPagoProperties properties) {
        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(chain -> {
                    Request original = chain.request();
                    Request request = original.newBuilder()
                            .header("Authorization", "Bearer " + properties.getToken())
                            .header("Content-Type", "application/json")
                            .build();
                    return chain.proceed(request);
                }).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(properties.getBaseUrl())
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MercadoPagoClient.class);
    }
}
