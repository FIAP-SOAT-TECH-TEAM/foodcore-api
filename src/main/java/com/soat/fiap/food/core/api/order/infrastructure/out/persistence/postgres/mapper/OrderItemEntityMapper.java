package com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.soat.fiap.food.core.api.order.core.domain.model.OrderItem;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.dto.OrderItemDTO;
import com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.entity.OrderItemEntity;
import com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.mapper.shared.OrderNumberMapper;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.DoIgnore;

/**
 * Mapper que converte entre a entidade de domínio OrderItem e a entidade JPA
 * OrderItemEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrderNumberMapper.class})
public interface OrderItemEntityMapper {

	@Mapping(target = "order", ignore = true)
	OrderItem toDomain(OrderItemEntity entity, @Context CycleAvoidingMappingContext context);

	List<OrderItem> toDomainList(List<OrderItemEntity> entities, @Context CycleAvoidingMappingContext context);

	@Mapping(target = "order", ignore = true)
	OrderItemEntity toEntity(OrderItemDTO dto, @Context CycleAvoidingMappingContext context);

	List<OrderItemEntity> toEntityList(List<OrderItemDTO> dto, @Context CycleAvoidingMappingContext context);

	OrderItemDTO toDTO(OrderItemEntity entity, @Context CycleAvoidingMappingContext context);

	@DoIgnore
	default OrderItem toDomain(OrderItemEntity entity) {
		return toDomain(entity, new CycleAvoidingMappingContext());
	}

	@DoIgnore
	default List<OrderItem> toDomainList(List<OrderItemEntity> entities) {
		return toDomainList(entities, new CycleAvoidingMappingContext());
	}

	// @DoIgnore
	// @Mapping(target = "order", ignore = true)
	// default OrderItemEntity toEntity(OrderItemDTO dto) {
	// return toEntity(dto, new CycleAvoidingMappingContext());
	// }
}
