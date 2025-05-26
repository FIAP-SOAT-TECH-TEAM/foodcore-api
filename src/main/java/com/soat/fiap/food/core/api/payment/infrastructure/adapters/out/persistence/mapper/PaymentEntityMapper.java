package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.persistence.mapper;

import com.soat.fiap.food.core.api.payment.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.persistence.entity.PaymentEntity;
import com.soat.fiap.food.core.api.shared.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.mapper.DoIgnore;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte entre a entidade de domínio Payment e a entidade JPA PaymentEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentEntityMapper {

    Payment toDomain(PaymentEntity entity, @Context CycleAvoidingMappingContext context);

    List<Payment> toDomainList(List<PaymentEntity> entities, @Context CycleAvoidingMappingContext context);

    PaymentEntity toEntity(Payment domain, @Context CycleAvoidingMappingContext context);

    @DoIgnore
    default Payment toDomain(PaymentEntity entity) {
        return toDomain(entity, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<Payment> toDomainList(List<PaymentEntity> entities) {
        return toDomainList(entities, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default PaymentEntity toEntity(Payment domain) {
        return toEntity(domain, new CycleAvoidingMappingContext());
    }
}