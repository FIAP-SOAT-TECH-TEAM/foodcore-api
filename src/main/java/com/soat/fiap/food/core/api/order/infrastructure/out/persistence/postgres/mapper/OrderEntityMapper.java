package com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.mapper;

import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.entity.OrderEntity;
import com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.mapper.shared.OrderNumberMapper;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.DoIgnore;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte entre a entidade de domínio Order e a entidade JPA OrderEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {
        OrderItemEntityMapper.class,
        OrderNumberMapper .class
})
public interface OrderEntityMapper {

    Order toDomain(OrderEntity entity, @Context CycleAvoidingMappingContext context);

    List<Order> toDomainList(List<OrderEntity> entities, @Context CycleAvoidingMappingContext context);

    OrderEntity toEntity(Order domain, @Context CycleAvoidingMappingContext context);

    @DoIgnore
    default Order toDomain(OrderEntity entity) {
        return toDomain(entity, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<Order> toDomainList(List<OrderEntity> entities) {
        return toDomainList(entities, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default OrderEntity toEntity(Order domain) {
        return toEntity(domain, new CycleAvoidingMappingContext());
    }
}