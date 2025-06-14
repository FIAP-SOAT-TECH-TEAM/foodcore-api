package com.soat.fiap.food.core.api.payment.application.mapper.response;

import com.soat.fiap.food.core.api.payment.application.dto.response.QrCodeResponse;
import com.soat.fiap.food.core.api.payment.domain.model.Payment;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.DoIgnore;
import org.mapstruct.Context;
import org.mapstruct.Mapper;

/**
 * Mapper para resposta de obtenção do qr code de um pedido
 */
@Mapper(componentModel = "spring")
public interface QrCodeResponseMapper {

    QrCodeResponse toResponse(Payment payment, @Context CycleAvoidingMappingContext context);

    @DoIgnore
    default QrCodeResponse toResponse(Payment payment) {
        return toResponse(payment, new CycleAvoidingMappingContext());
    }
}
