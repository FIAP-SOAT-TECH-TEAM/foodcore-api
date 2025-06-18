package com.soat.fiap.food.core.api.payment.infrastructure.out.persistence.postgres.mapper;

import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.infrastructure.out.persistence.postgres.entity.PaymentEntity;
import com.soat.fiap.food.core.api.payment.infrastructure.out.persistence.postgres.mapper.shared.QrCodeMapper;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.DoIgnore;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte entre a entidade de domínio Payment e a entidade JPA PaymentEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = QrCodeMapper.class)
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