package com.soat.fiap.food.core.api.order.infrastructure.in.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.soat.fiap.food.core.api.catalog.core.domain.events.ProductCreatedEvent;

import lombok.extern.slf4j.Slf4j;

/**
 * Ouvinte de eventos de catalogo no módulo de pedidos
 */
@Component @Slf4j
public class OrderProductEventListener {

	/**
	 * Processa o evento de produto criado
	 *
	 * @param event
	 *            Evento de produto criado
	 */
	@EventListener @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handleProductCreatedEvent(ProductCreatedEvent event) {
		log.info("Módulo Order: Notificado da criação do produto: {} (ID: {}), com preço: {}", event.getProductName(),
				event.getProductId(), event.getPrice());
	}
}
