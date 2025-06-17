package com.soat.fiap.food.core.api.catalog.infrastructure.in.event.listener;

import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.controller.web.api.product.UpdateProductStockForCanceledItemsController;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.controller.web.api.product.UpdateProductStockForCreatedItemsController;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.DataSource;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderCanceledEvent;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Ouvinte de eventos de pedido no módulo de catalogo
 */
@Component
@Slf4j
public class CatalogOrderEventListener {

    private final DataSource dataSource;

    public CatalogOrderEventListener(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    /**
     * Processa o evento de pedido criado
     * 
     * @param event Evento de pedido criado
     */
    @EventListener
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Módulo Catalogo: Notificado de criação de pedido: {}),",
                event.getId());

        UpdateProductStockForCreatedItemsController.updateProductStockForCreatedItems(event, dataSource);

        log.info("Quantidade em estoque atualizada para: {} produtos.", event.getItems().size());
    }

    /**
     * Processa o evento de pedido cancelado
     *
     * @param event Evento de pedido cancelado
     */
    @EventListener
    public void handleOrderCanceledEvent(OrderCanceledEvent event) {
        log.info("Módulo Catalogo: Notificado de cancelamento de pedido: {}),",
                event.getId());

        UpdateProductStockForCanceledItemsController.updateProductStockForCanceledItems(event, dataSource);

        log.info("Quantidade em estoque atualizada para: {} produtos.", event.getItems().size());
    }
} 