package com.soat.fiap.food.core.order.infrastructure.common.event.rabbitmq.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração do RabbitMQ para o sistema.
 * <p>
 * Define as filas e exchanges utilizadas pela aplicação e fornece os beans
 * necessários para integração com o RabbitMQ.
 * </p>
 */
@Configuration
public class RabbitMqQueueConfig {

	/** Nome da exchange do tipo fanout para eventos de pedido criado. */
	public static final String ORDER_CREATED_FANOUT_EXCHANGE = "order.created.fanout.exchange";

	/** Fila para eventos de pedido criado, no módulo de pagamento. */
	public static final String ORDER_PAYMENT_CREATED_QUEUE = "order.payment.created.queue";

	/** Fila para eventos de pedido criado, no módulo de catálogo. */
	public static final String ORDER_CATALOG_CREATED_QUEUE = "order.catalog.created.queue";

	/** Fila para eventos de pedido cancelado. */
	public static final String ORDER_CANCELED_QUEUE = "order.canceled.queue";

	/** Fila para eventos de pagamento aprovado. */
	public static final String PAYMENT_APPROVED_QUEUE = "payment.approved.queue";

	/** Fila para eventos de pagamento expirado. */
	public static final String PAYMENT_EXPIRED_QUEUE = "payment.expired.queue";

	/** Fila para eventos de erro na inicialização do pagamento. */
	public static final String PAYMENT_INITIALIZATION_ERROR_QUEUE = "payment.initialization.error.queue";

	/**
	 * Declara a fila de pedidos cancelados no RabbitMQ.
	 *
	 * @return objeto Queue configurado como durável para eventos de pedido
	 *         cancelado.
	 */
	@Bean
	public Queue orderCanceledQueue() {
		return new Queue(ORDER_CANCELED_QUEUE, true);
	}

	/**
	 * Declara a exchange do tipo fanout para distribuição de eventos de pedidos
	 * criados.
	 *
	 * @return objeto FanoutExchange configurado com o nome definido.
	 */
	@Bean
	public FanoutExchange orderFanoutExchange() {
		return new FanoutExchange(ORDER_CREATED_FANOUT_EXCHANGE);
	}

	/**
	 * Declara a fila de pedidos criados no módulo de pagamento no RabbitMQ.
	 *
	 * @return objeto Queue configurado como durável para eventos de pedido criado.
	 */
	@Bean
	public Queue orderPaymentCreatedQueue() {
		return new Queue(ORDER_PAYMENT_CREATED_QUEUE, true);
	}

	/**
	 * Declara a fila de pedidos criados no módulo de catálogo no RabbitMQ.
	 *
	 * @return objeto Queue configurado como durável para eventos de pedido criado.
	 */
	@Bean
	public Queue orderCatalogCreatedQueue() {
		return new Queue(ORDER_CATALOG_CREATED_QUEUE, true);
	}

	/**
	 * Cria a associação (binding) entre a fila de pedidos do módulo de pagamento e
	 * a exchange de pedidos criados.
	 *
	 * @param orderPaymentCreatedQueue
	 *            fila de pedidos do módulo de pagamento
	 * @param orderFanoutExchange
	 *            exchange fanout para pedidos criados
	 * @return objeto Binding que liga a fila à exchange
	 */
	@Bean
	public Binding bindingOrderPayment(Queue orderPaymentCreatedQueue, FanoutExchange orderFanoutExchange) {
		return BindingBuilder.bind(orderPaymentCreatedQueue).to(orderFanoutExchange);
	}

	/**
	 * Cria a associação (binding) entre a fila de pedidos do módulo de catálogo e a
	 * exchange de pedidos criados.
	 *
	 * @param orderCatalogCreatedQueue
	 *            fila de pedidos do módulo de catálogo
	 * @param orderFanoutExchange
	 *            exchange fanout para pedidos criados
	 * @return objeto Binding que liga a fila à exchange
	 */
	@Bean
	public Binding bindingOrderCatalog(Queue orderCatalogCreatedQueue, FanoutExchange orderFanoutExchange) {
		return BindingBuilder.bind(orderCatalogCreatedQueue).to(orderFanoutExchange);
	}
}
