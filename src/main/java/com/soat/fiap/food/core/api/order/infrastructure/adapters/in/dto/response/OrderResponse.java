package com.soat.fiap.food.core.api.order.infrastructure.adapters.in.dto.response;

import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de resposta para pedidos
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Resposta com dados de um pedido")
public class OrderResponse {
    
    @Schema(description = "ID do pedido", example = "1")
    private Long id;
    
    @Schema(description = "Número do pedido", example = "ORD-12345678")
    private String orderNumber;
    
    @Schema(description = "Status do pedido", example = "PENDING")
    private OrderStatus status;
    
    @Schema(description = "Descrição do status do pedido", example = "Pendente")
    private String statusDescription;
    
    @Schema(description = "ID do usuário (opcional)", example = "1")
    private Long userId;
    
    @Schema(description = "Nome do usuário (opcional)", example = "João Silva")
    private String userName;
    
    @Schema(description = "Valor total do pedido", example = "75.90")
    private BigDecimal totalAmount;
    
    @Schema(description = "Itens do pedido")
    private List<OrderItemResponse> items;
    
    @Schema(description = "Data de criação", example = "2023-06-15T14:30:15")
    private LocalDateTime createdAt;
    
    @Schema(description = "Data de atualização", example = "2023-06-15T14:45:22")
    private LocalDateTime updatedAt;
    
    /**
     * DTO para resposta de item de pedido
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Schema(description = "Item de um pedido")
    public static class OrderItemResponse {
        
        @Schema(description = "ID do item", example = "1")
        private Long id;
        
        @Schema(description = "ID do produto", example = "3")
        private Long productId;
        
        @Schema(description = "Nome do produto", example = "X-Burger")
        private String productName;
        
        @Schema(description = "Quantidade", example = "2")
        private Integer quantity;
        
        @Schema(description = "Preço unitário", example = "25.90")
        private BigDecimal unitPrice;
        
        @Schema(description = "Subtotal do item", example = "51.80")
        private BigDecimal subtotal;
    }
} 