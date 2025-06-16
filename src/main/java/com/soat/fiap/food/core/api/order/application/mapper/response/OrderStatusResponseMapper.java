package com.soat.fiap.food.core.api.order.application.mapper.response;

import com.soat.fiap.food.core.api.order.application.dto.response.OrderStatusResponse;
import com.soat.fiap.food.core.api.order.domain.model.Order;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.DoIgnore;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper para resposta após atualização do status de um pedido
 */
@Mapper(componentModel = "spring")
public interface OrderStatusResponseMapper {

    @Mapping(target = "orderId", source = "id")
    OrderStatusResponse toResponse(Order order, @Context CycleAvoidingMappingContext context);

    @DoIgnore
    default OrderStatusResponse toResponse(Order order) {
        return toResponse(order, new CycleAvoidingMappingContext());
    }
}