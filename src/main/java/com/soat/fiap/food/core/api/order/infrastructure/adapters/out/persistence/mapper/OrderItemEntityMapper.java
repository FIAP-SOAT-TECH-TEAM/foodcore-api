package com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.mapper;

import com.soat.fiap.food.core.api.order.domain.model.OrderItem;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.entity.OrderItemEntity;
import com.soat.fiap.food.core.api.shared.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.mapper.DoIgnore;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte entre a entidade de domínio OrderItem e a entidade JPA OrderItemEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemEntityMapper {

    OrderItem toDomain(OrderItemEntity entity, @Context CycleAvoidingMappingContext context);

    List<OrderItem> toDomainList(List<OrderItemEntity> entities, @Context CycleAvoidingMappingContext context);

    OrderItemEntity toEntity(OrderItem domain, @Context CycleAvoidingMappingContext context);

    @DoIgnore
    default OrderItem toDomain(OrderItemEntity entity) {
        return toDomain(entity, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<OrderItem> toDomainList(List<OrderItemEntity> entities) {
        return toDomainList(entities, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default OrderItemEntity toEntity(OrderItem domain) {
        return toEntity(domain, new CycleAvoidingMappingContext());
    }
}