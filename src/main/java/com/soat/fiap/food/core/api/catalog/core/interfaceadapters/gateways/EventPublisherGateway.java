package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways;

import com.soat.fiap.food.core.api.shared.infrastructure.shared.EventPublisher;

/**
 * Gateway pela publicação de eventos de domínio.
 */
public class EventPublisherGateway {

    private final EventPublisher eventPublisher;

    public EventPublisherGateway(
            EventPublisher eventPublisher
    ) {
        this.eventPublisher = eventPublisher;
    }

    /**
     * Publica um evento de domínio.
     *
     * @param event Evento de domínio que será publicado
     */
    public void publishEvent(Object event) {
        eventPublisher.publishEvent(event);
    }
}