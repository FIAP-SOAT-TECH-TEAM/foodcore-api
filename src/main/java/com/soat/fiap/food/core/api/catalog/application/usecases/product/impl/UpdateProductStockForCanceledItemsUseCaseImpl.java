package com.soat.fiap.food.core.api.catalog.application.usecases.product.impl;

import com.soat.fiap.food.core.api.catalog.application.usecases.product.UpdateProductStockForCanceledItemsUseCase;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.order.domain.events.OrderItemCanceledEvent;
import com.soat.fiap.food.core.api.order.domain.exceptions.OrderItemNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Caso de uso (implementação concreta): Atualizar quantidade em estoque de produtos de acordo com a quantidade cancelada em um pedido.
 *
 */
@Slf4j
public class UpdateProductStockForCanceledItemsUseCaseImpl implements UpdateProductStockForCanceledItemsUseCase {

    private final CatalogGateway catalogGateway;

    public UpdateProductStockForCanceledItemsUseCaseImpl(
            CatalogGateway catalogRepository
    ) {
        this.catalogGateway = catalogRepository;
    }

    /**
     * Atualiza quantidade em estoque de produtos de acordo com a quantidade solicitada em um pedido.
     *
     * @param orderItemCanceledEvents eventos de cancelamento de item de pedido
     */
    @Override
    public void updateStockForCanceledItems(List<OrderItemCanceledEvent> orderItemCanceledEvents) {
        if (orderItemCanceledEvents.isEmpty()) {
            throw new OrderItemNotFoundException("Lista de itens de pedido está vazia. Não é possível recuperar produtos para atualização de quantidade em estoque.");
        }

        for (OrderItemCanceledEvent orderItemCanceledEvent : orderItemCanceledEvents) {
            var catalog = catalogGateway.findByProductId(orderItemCanceledEvent.getProductId());
            if (catalog.isEmpty()) {
                throw new CatalogNotFoundException("Catálogo do produto do item de pedido não encontrado. Não é possível atualizar quantidade em estoque.");
            }

            var currentProductQuantity = catalog.get().getProductStockQuantity(orderItemCanceledEvent.getProductId());
            var newProductQuantity =  currentProductQuantity + orderItemCanceledEvent.getQuantity();

            log.info("Iniciando atualização de quantidade em estoque: ProductId {}, atual: {}, nova: {}", orderItemCanceledEvent.getProductId(), currentProductQuantity, newProductQuantity);

            catalog.get().updateProductStockQuantity(orderItemCanceledEvent.getProductId(), newProductQuantity);

            catalogGateway.save(catalog.get());

            log.info("Atualização de quantidade em estoque realizada com sucesso! ProductId {}", orderItemCanceledEvent.getProductId());
        }

    }
}
