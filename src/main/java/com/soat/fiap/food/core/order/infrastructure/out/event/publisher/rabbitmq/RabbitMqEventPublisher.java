package com.soat.fiap.food.core.order.infrastructure.out.event.publisher.rabbitmq;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.soat.fiap.food.core.order.core.interfaceadapters.dto.events.OrderCanceledEventDto;
import com.soat.fiap.food.core.order.core.interfaceadapters.dto.events.OrderCreatedEventDto;
import com.soat.fiap.food.core.order.infrastructure.common.event.rabbitmq.config.RabbitMqQueueConfig;
import com.soat.fiap.food.core.order.infrastructure.common.source.EventPublisherSource;

/**
 * Implementação do {@link EventPublisherSource} usando RabbitMQ.
 * <p>
 * Esta classe envia eventos de domínio para filas RabbitMQ correspondentes.
 * Cada método publica um tipo de evento específico.
 * </p>
 */
@Component
public class RabbitMqEventPublisher implements EventPublisherSource {

	@Autowired
	private RabbitTemplate rabbitTemplate;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishOrderCreatedEvent(OrderCreatedEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqQueueConfig.ORDER_CREATED_FANOUT_EXCHANGE, "", event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishOrderCanceledEvent(OrderCanceledEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqQueueConfig.ORDER_CANCELED_QUEUE, event);
	}
}
