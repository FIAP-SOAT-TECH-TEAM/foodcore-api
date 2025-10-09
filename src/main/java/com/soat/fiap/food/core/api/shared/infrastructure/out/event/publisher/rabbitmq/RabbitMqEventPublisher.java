package com.soat.fiap.food.core.api.shared.infrastructure.out.event.publisher.rabbitmq;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.events.*;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.soat.fiap.food.core.api.shared.infrastructure.out.event.publisher.rabbitmq.config.RabbitMqConfig;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;

/**
 * Implementação do {@link EventPublisherSource} usando RabbitMQ.
 * <p>
 * Esta classe envia eventos de domínio para filas RabbitMQ correspondentes.
 * Cada método publica um tipo de evento específico.
 * </p>
 */
@Component
public class RabbitMqEventPublisher implements EventPublisherSource {

	private final RabbitTemplate rabbitTemplate;

	public RabbitMqEventPublisher(RabbitTemplate rabbitTemplate) {
		this.rabbitTemplate = rabbitTemplate;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishOrderCreatedEvent(OrderCreatedEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqConfig.ORDER_CREATED_STREAM, event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishOrderCanceledEvent(OrderCanceledEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqConfig.ORDER_CANCELED_QUEUE, event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishProductCreatedEvent(ProductCreatedEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqConfig.PRODUCT_CREATED_QUEUE, event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishPaymentApprovedEvent(PaymentApprovedEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqConfig.PAYMENT_APPROVED_QUEUE, event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishPaymentExpiredEvent(PaymentExpiredEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqConfig.PAYMENT_EXPIRED_QUEUE, event);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void publishPaymentInitializationErrorEvent(PaymentInitializationErrorEventDto event) {
		rabbitTemplate.convertAndSend(RabbitMqConfig.PAYMENT_INITIALIZATION_ERROR_QUEUE, event);
	}
}
