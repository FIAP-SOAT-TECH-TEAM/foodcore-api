package com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways;

import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;

/**
 * Gateway pela publicação de eventos de domínio.
 */
public class EventPublisherGateway {

	private final EventPublisherSource eventPublisherSource;

	public EventPublisherGateway(EventPublisherSource eventPublisherSource) {
		this.eventPublisherSource = eventPublisherSource;
	}

	/**
	 * Publica um evento de domínio.
	 *
	 * @param event
	 *            Evento de domínio que será publicado
	 */
	public void publishEvent(Object event) {
		eventPublisherSource.publishEvent(event);
	}
}
