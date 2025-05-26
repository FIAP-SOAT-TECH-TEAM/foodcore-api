package com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.mapper;

import com.soat.fiap.food.core.api.order.domain.model.OrderPayment;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.entity.OrderPaymentEntity;
import com.soat.fiap.food.core.api.shared.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.mapper.DoIgnore;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte entre a entidade de domínio OrderPayment e a entidade JPA OrderPaymentEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderPaymentEntityMapper {

    OrderPayment toDomain(OrderPaymentEntity entity, @Context CycleAvoidingMappingContext context);

    List<OrderPayment> toDomainList(List<OrderPaymentEntity> entities, @Context CycleAvoidingMappingContext context);

    OrderPaymentEntity toEntity(OrderPayment domain, @Context CycleAvoidingMappingContext context);

    @DoIgnore
    default OrderPayment toDomain(OrderPaymentEntity entity) {
        return toDomain(entity, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<OrderPayment> toDomainList(List<OrderPaymentEntity> entities) {
        return toDomainList(entities, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default OrderPaymentEntity toEntity(OrderPayment domain) {
        return toEntity(domain, new CycleAvoidingMappingContext());
    }
}