package com.soat.fiap.food.core.api.catalog.application.usecases.product;

import com.soat.fiap.food.core.api.order.domain.events.OrderItemCreatedEvent;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Caso de uso: Atualizar quantidade em estoque de produtos de acordo com a quantidade solicitada em um pedido.
 *
 */
public interface UpdateProductStockForCreatedItemsUseCase {

    /**
     * Atualiza quantidade em estoque de produtos de acordo com a quantidade solicitada em um pedido.
     *
     * @param orderItemCreatedEvents eventos de criação de item de pedido
     */
    void updateStockForCreatedItems(List<OrderItemCreatedEvent> orderItemCreatedEvents);
}
