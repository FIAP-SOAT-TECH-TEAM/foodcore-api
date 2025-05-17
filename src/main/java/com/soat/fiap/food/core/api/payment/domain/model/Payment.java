package com.soat.fiap.food.core.api.payment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade de domínio que representa um pagamento
 * AGGREGATE ROOT:
 *  - Toda modificação de entidades internas do agregado devem passar pela entidade raíz;
 *  - Único ponto de entrada para qualquer entidade interna do agregado (Lei de Demeter);
 *  - Entidades dentro deste agregado podem se referenciar via id ou objeto;
 *  - Entidades de outros agregados só podem referenciar esta entidade raiz, e isto deve ser via Id;
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Payment {
    private Long id;
    private Long customerId;
    private PaymentMethod type;
    private LocalDateTime expiresIn;
    private String tid;
    private BigDecimal amount;
    private String qrCodeUrl;
    private String observations;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
} 