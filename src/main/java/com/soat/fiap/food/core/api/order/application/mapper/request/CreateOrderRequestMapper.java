package com.soat.fiap.food.core.api.order.application.mapper.request;

import com.soat.fiap.food.core.api.order.application.dto.request.CreateOrderRequest;
import com.soat.fiap.food.core.api.order.domain.model.Order;
import org.mapstruct.Mapper;

/**
 * Mapper responsável por converter {@link CreateOrderRequest} em {@link Order}.
 */
@Mapper(componentModel = "spring", uses = OrderItemRequestMapper.class)
public interface CreateOrderRequestMapper {

    /**
     * Converte um {@link CreateOrderRequest} para um {@link Order}.
     *
     * @param request     objeto com os dados do pedido.
     * @return instância da entidade {@link Order}.
     */
    Order toDomain(CreateOrderRequest request);
}
