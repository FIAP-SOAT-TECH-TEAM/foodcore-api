package com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events;

import com.soat.fiap.food.core.api.order.core.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * DTO utilizado para representar dados do evento de domínio {@link OrderCreatedEvent}. Serve como objeto
 * de transferência entre o domínio e o mundo externo (DataSource).
 */
@Data
public class OrderCreatedEventDto {
	public Long id;
	public String orderNumber;
	public OrderStatus status;
	public String statusDescription;
	public String userId;
	public BigDecimal totalAmount;
	public List<OrderItemCreatedEventDto> items;
}
