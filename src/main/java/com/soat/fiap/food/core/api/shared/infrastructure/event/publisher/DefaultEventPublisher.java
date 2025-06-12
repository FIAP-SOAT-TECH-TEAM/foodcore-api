package com.soat.fiap.food.core.api.shared.infrastructure.event.publisher;

import com.soat.fiap.food.core.api.shared.infrastructure.shared.EventPublisher;
import org.springframework.context.ApplicationEventPublisher;

/**
 * Implementação concreta: EventPublisher padrão para publicação de eventos de domínio.
 */
public class DefaultEventPublisher implements EventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public DefaultEventPublisher(
            ApplicationEventPublisher applicationEventPublisher
    ) {
        this.applicationEventPublisher =  applicationEventPublisher;
    }

    /**
     * Publica um evento de domínio.
     *
     * @param event Evento de domínio que será publicado
     */
    public void publishEvent(Object event) {
        applicationEventPublisher.publishEvent(event);
    }

}