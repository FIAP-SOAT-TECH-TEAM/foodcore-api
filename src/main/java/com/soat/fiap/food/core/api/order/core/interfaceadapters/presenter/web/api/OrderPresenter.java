package com.soat.fiap.food.core.api.order.core.interfaceadapters.presenter.web.api;

import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.response.OrderItemResponse;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.response.OrderResponse;

import java.util.List;

/**
 * Presenter responsável por converter objetos do domínio {@link Order}
 * em objetos de resposta {@link OrderResponse} utilizados na camada de API web (web.api).
 */
public class OrderPresenter {

    /**
     * Converte uma instância da entidade {@link Order} para um {@link OrderResponse}.
     *
     * @param order A entidade de domínio {@link Order} a ser convertida.
     * @return Um DTO {@link OrderResponse} com os dados formatados para resposta HTTP.
     */
    public static OrderResponse toOrderResponse(Order order) {
        List<OrderItemResponse> itemResponses = OrderItemPresenter.toListOrderItemResponse(order.getOrderItems());

        return new OrderResponse(
                order.getId(),
                order.getOrderNumber(),
                order.getOrderStatus(),
                order.getOrderStatus().getDescription(),
                order.getUserId(),
                order.getAmount(),
                itemResponses,
                order.getCreatedAt(),
                order.getUpdatedAt()
        );
    }
}