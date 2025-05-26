package com.soat.fiap.food.core.api.order.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para adição de um item a um pedido existente
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requisição para adição de item a um pedido")
public class AddOrderItemRequest {
    
    @NotNull(message = "O ID do produto é obrigatório")
    @Positive(message = "O ID do produto deve ser positivo")
    @Schema(description = "ID do produto", example = "1", required = true)
    private Long productId;
    
    @NotNull(message = "A quantidade é obrigatória")
    @Positive(message = "A quantidade deve ser positiva")
    @Schema(description = "Quantidade do produto", example = "2", required = true)
    private Integer quantity;
} 