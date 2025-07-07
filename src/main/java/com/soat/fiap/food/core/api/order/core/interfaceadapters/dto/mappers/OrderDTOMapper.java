package com.soat.fiap.food.core.api.order.core.interfaceadapters.dto.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.dto.OrderDTO;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.dto.OrderItemDTO;

public class OrderDTOMapper {

	public static Order toDomain(OrderDTO dto) {
		return Order.fromDTO(dto);
	}

	public static OrderDTO toDTO(Order order) {
		List<OrderItemDTO> itemDTOs = order.getOrderItems().stream()
				.map(item -> new OrderItemDTO(
						item.getProductId(),
						item.getName(),
						item.getQuantity(),
						item.getOrderItemPrice(),
						item.getObservations()
				))
				.collect(Collectors.toList());

		return new OrderDTO(
				order.getId(),
				order.getUserId(),
				order.getOrderStatus(),
				itemDTOs,
				order.getAuditInfo().getCreatedAt(),
				order.getAuditInfo().getUpdatedAt()
		);
	}
}
