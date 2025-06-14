package com.soat.fiap.food.core.api.shared.infrastructure.common.source;

/**
 * EventPublisher para publicação de eventos.
 */
public interface EventPublisherSource {

    /**
     * Publica um evento de domínio.
     *
     * @param event Evento de domínio que será publicado
     */
    void publishEvent(Object event);
}