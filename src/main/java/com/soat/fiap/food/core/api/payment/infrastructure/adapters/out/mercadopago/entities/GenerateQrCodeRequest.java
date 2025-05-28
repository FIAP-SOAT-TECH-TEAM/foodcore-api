package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.entities;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Entidade que representa o body de geração de um QrCode
 */
@Data
public class GenerateQrCodeRequest {
    private String external_reference;
    private String title;
    private String description;
    private String notification_url;
    private BigDecimal total_amount;
    private List<GenerateQrCodeItemRequest> items;
}
