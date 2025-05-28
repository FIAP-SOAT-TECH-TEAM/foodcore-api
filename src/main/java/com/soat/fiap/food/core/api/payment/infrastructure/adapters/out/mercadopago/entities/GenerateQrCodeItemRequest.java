package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.mercadopago.entities;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class GenerateQrCodeItemRequest {
    private String sku_number;
    private String category;
    private String title;
    private String description;
    private BigDecimal unit_price;
    private int quantity;
    private String unit_measure;
    private BigDecimal total_amount;
}
