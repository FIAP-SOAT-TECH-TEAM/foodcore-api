package com.soat.fiap.food.core.api.catalog.application.usecases.product.impl;

import com.soat.fiap.food.core.api.catalog.application.usecases.product.UpdateProductStockForCreatedItemsUseCase;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.order.domain.events.OrderItemCreatedEvent;
import com.soat.fiap.food.core.api.order.domain.exceptions.OrderItemNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Caso de uso (implementação concreta): Atualizar quantidade em estoque de produtos de acordo com a quantidade solicitada em um pedido.
 *
 */
@Slf4j
public class UpdateProductStockForCreatedItemsUseCaseImpl implements UpdateProductStockForCreatedItemsUseCase {

    private final CatalogGateway catalogGateway;

    public UpdateProductStockForCreatedItemsUseCaseImpl(
            CatalogGateway catalogRepository
    ) {
        this.catalogGateway = catalogRepository;
    }

    /**
     * Atualiza quantidade em estoque de produtos de acordo com a quantidade solicitada em um pedido.
     *
     * @param orderItemCreatedEvents eventos de criação de item de pedido
     */
    @Override
    @Transactional
    public void updateStockForCreatedItems(List<OrderItemCreatedEvent> orderItemCreatedEvents) {
        if (orderItemCreatedEvents.isEmpty()) {
            throw new OrderItemNotFoundException("Lista de itens de pedido está vazia. Não é possível recuperar produtos para atualização de quantidade em estoque.");
        }

        for (OrderItemCreatedEvent orderItemCreatedEvent : orderItemCreatedEvents) {
            var catalog = catalogGateway.findByProductId(orderItemCreatedEvent.getProductId());
            if (catalog.isEmpty()) {
                throw new CatalogNotFoundException("Catálogo do produto do item de pedido não encontrado. Não é possível atualizar quantidade em estoque.");
            }

            var currentProductQuantity = catalog.get().getProductStockQuantity(orderItemCreatedEvent.getProductId());
            var newProductQuantity =  currentProductQuantity - orderItemCreatedEvent.getQuantity();

            log.info("Iniciando atualização de quantidade em estoque: ProductId {}, atual: {}, nova: {}", orderItemCreatedEvent.getProductId(), currentProductQuantity, newProductQuantity);

            catalog.get().updateProductStockQuantity(orderItemCreatedEvent.getProductId(), newProductQuantity);

            catalogGateway.save(catalog.get());

            log.info("Atualização de quantidade em estoque realizada com sucesso! ProductId {}", orderItemCreatedEvent.getProductId());
        }

    }
}
