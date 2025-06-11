package com.soat.fiap.food.core.api.catalog.application.usecases.product;

import com.soat.fiap.food.core.api.order.domain.events.OrderItemCanceledEvent;
import com.soat.fiap.food.core.api.order.domain.events.OrderItemCreatedEvent;

import java.util.List;

/**
 * Caso de uso: Atualizar quantidade em estoque de produtos de acordo com a quantidade solicitada em um pedido.
 *
 */
public interface UpdateProductStockForCanceledItemsUseCase {

    /**
     * Atualiza quantidade em estoque de produtos de acordo com a quantidade solicitada em um pedido.
     *
     * @param orderItemCanceledEvents eventos de cancelamento de item de pedido
     */
    void updateStockForCanceledItems(List<OrderItemCanceledEvent> orderItemCanceledEvents);
}
