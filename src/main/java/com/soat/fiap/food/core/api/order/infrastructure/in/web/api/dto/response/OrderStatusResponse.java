package com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.response;

import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO para resposta após atualização do status de um pedido
 */
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OrderStatusResponse {
	@Schema(description = "ID do pedido")
	private Long orderId;
	@Schema(description = "Status do pedido")
	private OrderStatus orderStatus;
}
