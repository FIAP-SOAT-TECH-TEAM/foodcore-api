package com.soat.fiap.food.core.order.infrastructure.common.event.azsvcbus.config;

import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração do Azure Service Bus.
 * <p>
 */
@Configuration
public class ServiceBusConfig {

	/** Nome do tópico para eventos de pedido criado. */
	public static final String ORDER_CREATED_TOPIC = "order.created.topic";

	/** Fila para eventos de pedido cancelado. */
	public static final String ORDER_CANCELED_QUEUE = "order.canceled.queue";

	/** Fila para eventos de pagamento aprovado. */
	public static final String PAYMENT_APPROVED_QUEUE = "payment.approved.queue";

	/** Fila para eventos de pagamento expirado. */
	public static final String PAYMENT_EXPIRED_QUEUE = "payment.expired.queue";

	/** Nome da subscription para eventos de pedido criado. */
	public static final String PAYMENT_ORDER_CREATED_TOPIC_SUBSCRIPTION = "payment.order.created.topic.subscription";
}
