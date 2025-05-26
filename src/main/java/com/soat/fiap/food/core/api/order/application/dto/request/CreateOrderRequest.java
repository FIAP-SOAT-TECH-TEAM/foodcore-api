package com.soat.fiap.food.core.api.order.application.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * DTO para criação de um novo pedido
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Requisição para criação de um novo pedido")
public class CreateOrderRequest {
    
    @Schema(description = "ID do cliente (opcional)", example = "1")
    private Long customerId;
    
    @NotEmpty(message = "A lista de itens não pode estar vazia")
    @Valid
    @Schema(description = "Lista de itens do pedido", required = true)
    private List<OrderItemRequest> items;
    
    /**
     * DTO para representar um item de pedido na requisição
     */
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Item do pedido")
    public static class OrderItemRequest {
        
        @NotNull(message = "O ID do produto é obrigatório")
        @Positive(message = "O ID do produto deve ser positivo")
        @Schema(description = "ID do produto", example = "1", required = true)
        private Long productId;
        
        @NotNull(message = "A quantidade é obrigatória")
        @Positive(message = "A quantidade deve ser positiva")
        @Schema(description = "Quantidade do produto", example = "2", required = true)
        private Integer quantity;
    }
} 