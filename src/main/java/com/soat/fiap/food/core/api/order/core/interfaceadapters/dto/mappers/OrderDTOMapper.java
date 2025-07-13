package com.soat.fiap.food.core.api.order.core.interfaceadapters.dto.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.dto.OrderDTO;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.dto.OrderItemDTO;

public class OrderDTOMapper {

	/**
	 * Cria uma instância de {@link Order} a partir de um {@link OrderDTO}.
	 *
	 * @param dto
	 *            DTO de pedido contendo os dados a serem convertidos
	 * @return Instância de um Order{@link Order}
	 */
	public static Order toDomain(OrderDTO dto) {
		return Order.fromDTO(dto);
	}

	/**
	 * Cria uma instância de {@link OrderDTO} a partir de um {@link Order}.
	 *
	 * @param order
	 *            Entidade de domínio de pedido contendo os dados a serem
	 *            convertidos
	 * @return Instância do DTO de pedido {@link OrderDTO}
	 */
	public static OrderDTO toDTO(Order order) {
		List<OrderItemDTO> itemDTOs = order.getOrderItems()
				.stream()
				.map(item -> new OrderItemDTO(item.getId(), item.getProductId(), item.getName(),
						item.getOrderItemPrice().quantity(), item.getOrderItemPrice().unitPrice(),
						item.getObservations(), item.getCreatedAt(), item.getUpdatedAt()))
				.collect(Collectors.toList());

		return new OrderDTO(order.getId(), order.getUserId(), order.getOrderNumber(), order.getOrderStatus(),
				order.getAmount(), itemDTOs, order.getCreatedAt(), order.getUpdatedAt());
	}
}
