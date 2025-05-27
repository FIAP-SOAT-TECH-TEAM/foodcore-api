package com.soat.fiap.food.core.api.order.application.mapper.response;

import com.soat.fiap.food.core.api.order.application.dto.response.OrderItemResponse;
import com.soat.fiap.food.core.api.order.domain.model.OrderItem;
import com.soat.fiap.food.core.api.shared.mapper.AuditInfoMapper;
import com.soat.fiap.food.core.api.shared.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.mapper.DoIgnore;
import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte a entidade {@link OrderItem} para o DTO {@link OrderItemResponse}.
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {AuditInfoMapper.class})
public interface OrderItemResponseMapper {

    @Mapping(source = "auditInfo", target = "createdAt", qualifiedByName = "mapCreatedAt")
    @Mapping(source = "auditInfo", target = "updatedAt", qualifiedByName = "mapUpdatedAt")
    OrderItemResponse toResponse(OrderItem orderItem, @Context CycleAvoidingMappingContext context);

    @Mapping(source = "auditInfo", target = "createdAt", qualifiedByName = "mapCreatedAt")
    @Mapping(source = "auditInfo", target = "updatedAt", qualifiedByName = "mapUpdatedAt")
    List<OrderItemResponse> toResponseList(List<OrderItem> orderItems, @Context CycleAvoidingMappingContext context);

    @DoIgnore
    default OrderItemResponse toResponse(OrderItem orderItem) {
        return toResponse(orderItem, new CycleAvoidingMappingContext());
    }

    @DoIgnore
    default List<OrderItemResponse> toResponseList(List<OrderItem> orderItems) {
        return toResponseList(orderItems, new CycleAvoidingMappingContext());
    }
}