package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.config;

import com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.client.MercadoPagoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

@Configuration
public class RetrofitConfig {

    @Bean
    public MercadoPagoClient mercadoPagoClient() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.mercadopago.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        return retrofit.create(MercadoPagoClient.class);
    }
}
