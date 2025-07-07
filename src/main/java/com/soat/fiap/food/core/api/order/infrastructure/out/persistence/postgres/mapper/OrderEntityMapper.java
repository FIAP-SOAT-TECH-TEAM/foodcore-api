package com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.dto.OrderDTO;
import com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.entity.OrderEntity;
import com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.mapper.shared.OrderNumberMapper;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.DoIgnore;

/**
 * Mapper que converte entre a entidade de domínio Order e a entidade JPA
 * OrderEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrderItemEntityMapper.class,
		OrderNumberMapper.class})
public interface OrderEntityMapper {

	Order toDomain(OrderEntity entity, @Context CycleAvoidingMappingContext context);

	List<Order> toDomainList(List<OrderEntity> entities, @Context CycleAvoidingMappingContext context);

	OrderEntity toEntity(OrderDTO dto, @Context CycleAvoidingMappingContext context);

	OrderDTO toDTO(OrderEntity entity);

	@DoIgnore
	default Order toDomain(OrderEntity entity) {
		return toDomain(entity, new CycleAvoidingMappingContext());
	}

	@DoIgnore
	default List<Order> toDomainList(List<OrderEntity> entities) {
		return toDomainList(entities, new CycleAvoidingMappingContext());
	}

	@DoIgnore
	default OrderEntity toEntity(OrderDTO dto) {
		return toEntity(dto, new CycleAvoidingMappingContext());
	}

}
