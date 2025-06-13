package com.soat.fiap.food.core.api.shared.infrastructure.common;

/**
 * EventPublisher para publicação de eventos.
 */
public interface EventPublisher {

    /**
     * Publica um evento de domínio.
     *
     * @param event Evento de domínio que será publicado
     */
    void publishEvent(Object event);
}