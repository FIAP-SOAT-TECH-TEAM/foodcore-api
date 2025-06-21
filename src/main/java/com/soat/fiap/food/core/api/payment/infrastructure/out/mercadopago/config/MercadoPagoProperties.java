package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "mercado-pago")
public class MercadoPagoProperties {
    private String baseUrl;
    private String token;
    private String userId;
    private String posId;
    private String notificationUrl;
}