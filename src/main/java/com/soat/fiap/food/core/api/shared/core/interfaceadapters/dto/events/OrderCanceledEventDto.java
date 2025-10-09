package com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events;

import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderCanceledEvent;

import lombok.Data;

import java.util.List;

/**
 * DTO utilizado para representar dados do evento de domínio {@link OrderCanceledEvent}. Serve como objeto
 * de transferência entre o domínio e o mundo externo (DataSource).
 */
@Data
public class OrderCanceledEventDto {
	public Long id;
	public OrderStatus status;
	public List<OrderItemCanceledEventDto> items;
}
