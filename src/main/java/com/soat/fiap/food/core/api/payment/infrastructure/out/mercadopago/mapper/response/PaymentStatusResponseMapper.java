package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.mapper.response;

import com.soat.fiap.food.core.api.payment.application.dto.response.PaymentStatusResponse;
import com.soat.fiap.food.core.api.payment.domain.model.Payment;
import com.soat.fiap.food.core.api.shared.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.mapper.DoIgnore;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

/**
 * Mapper para resposta de checagem de pagamento
 */
@Mapper(componentModel = "spring")
public interface PaymentStatusResponseMapper {

    PaymentStatusResponse toResponse(Payment payment, @Context CycleAvoidingMappingContext context);

    @DoIgnore
    default PaymentStatusResponse toResponse(Payment payment) {
        return toResponse(payment, new CycleAvoidingMappingContext());
    }
}
