package com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.mapper;

import java.util.List;

import org.mapstruct.*;

import com.soat.fiap.food.core.api.order.core.domain.model.OrderItem;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.dto.OrderItemDTO;
import com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.entity.OrderItemEntity;
import com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.mapper.shared.OrderItemPriceMapper;
import com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.mapper.shared.OrderNumberMapper;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;

/**
 * Mapper que converte entre a entidade de domínio OrderItem e a entidade JPA
 * OrderItemEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {OrderNumberMapper.class,
		OrderItemPriceMapper.class})
public interface OrderItemEntityMapper {

	@Mapping(target = "order", ignore = true)
	OrderItem toDomain(OrderItemEntity entity, @Context CycleAvoidingMappingContext context);

	List<OrderItem> toDomainList(List<OrderItemEntity> entities, @Context CycleAvoidingMappingContext context);

	@Mapping(target = "order", ignore = true)
	@Mapping(target = "orderItemPrice", source = ".", qualifiedByName = "fromQuantityAndPrice")
	OrderItemEntity toEntity(OrderItemDTO dto, @Context CycleAvoidingMappingContext context);

	@Mapping(target = "order", ignore = true)
	@Mapping(target = "orderItemPrice", source = ".", qualifiedByName = "mapToOrderItemPrice")
	List<OrderItemEntity> toEntityList(List<OrderItemDTO> dto, @Context CycleAvoidingMappingContext context);

	@Mapping(target = "quantity", source = "orderItemPrice", qualifiedByName = "extractQuantity")
	@Mapping(target = "price", source = "orderItemPrice", qualifiedByName = "extractPrice")
	OrderItemDTO toDTO(OrderItemEntity entity, @Context CycleAvoidingMappingContext context);

}
