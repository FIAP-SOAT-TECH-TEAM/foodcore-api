package com.soat.fiap.food.core.api.payment.application.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para requisição de processamento de pagamento
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProcessPaymentRequest {
    
    /**
     * Método de pagamento escolhido
     */
    @NotBlank(message = "O método de pagamento é obrigatório")
    private String paymentMethod;
} 