package com.soat.fiap.food.core.api.order.infrastructure.in.event.listener;

import com.soat.fiap.food.core.api.catalog.core.domain.events.ProductCreatedEvent;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * Ouvinte de eventos de catalogo no módulo de pedidos
 */
@Component
@Slf4j
public class OrderProductEventListener {

    /**
     * Processa o evento de produto criado
     * 
     * @param event Evento de produto criado
     */
    @EventListener
    public void handleProductCreatedEvent(ProductCreatedEvent event) {
        log.info("Módulo Order: Notificado da criação do produto: {} (ID: {}), com preço: {}",
                event.getProductName(), event.getProductId(), event.getPrice());
    }
} 