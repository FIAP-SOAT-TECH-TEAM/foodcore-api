package com.soat.fiap.food.core.api.shared.infrastructure.out.event.publisher;

import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

/**
 * Implementação concreta: EventPublisher padrão para publicação de eventos de domínio.
 */
@Component
public class DefaultEventPublisher implements EventPublisherSource {

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