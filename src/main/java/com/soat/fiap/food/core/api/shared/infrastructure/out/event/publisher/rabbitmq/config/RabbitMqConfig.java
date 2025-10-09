package com.soat.fiap.food.core.api.shared.infrastructure.out.event.publisher.rabbitmq.config;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Classe de configuração do RabbitMQ para o sistema.
 * <p>
 * Define as filas e streams utilizadas pela aplicação e fornece os beans necessários para
 * integração com o RabbitMQ.
 * </p>
 */
@Configuration
public class RabbitMqConfig {

	/** Stream para eventos de pedido criado. */
	public static final String ORDER_CREATED_STREAM = "order.created.stream";

	/** Fila para eventos de pedido cancelado. */
	public static final String ORDER_CANCELED_QUEUE = "order.canceled.queue";

	/** Fila para eventos de produto criado. */
	public static final String PRODUCT_CREATED_QUEUE = "product.created.queue";

	/** Fila para eventos de pagamento aprovado. */
	public static final String PAYMENT_APPROVED_QUEUE = "payment.approved.queue";

	/** Fila para eventos de pagamento expirado. */
	public static final String PAYMENT_EXPIRED_QUEUE = "payment.expired.queue";

	/** Fila para eventos de erro na inicialização do pagamento. */
	public static final String PAYMENT_INITIALIZATION_ERROR_QUEUE = "payment.initialization.error.queue";

	/**
	 * Declara a stream de pedidos criados no RabbitMQ.
	 *
	 * @return objeto Stream configurado como durável para eventos de pedido criado.
	 */
	@Bean
	public Queue orderCreatedStream() {
		return QueueBuilder.durable(ORDER_CREATED_STREAM)
				.stream()
				.build();
	}

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
	 * Declara a fila de produtos criados no RabbitMQ.
	 *
	 * @return objeto Queue configurado como durável para eventos de produto criado.
	 */
	@Bean
	public Queue productCreatedQueue() {
		return new Queue(PRODUCT_CREATED_QUEUE, true);
	}

	/**
	 * Declara a fila de pagamentos aprovados no RabbitMQ.
	 *
	 * @return objeto Queue configurado como durável para eventos de pagamento
	 *         aprovado.
	 */
	@Bean
	public Queue paymentApprovedQueue() {
		return new Queue(PAYMENT_APPROVED_QUEUE, true);
	}

	/**
	 * Declara a fila de pagamentos expirados no RabbitMQ.
	 *
	 * @return objeto Queue configurado como durável para eventos de pagamento
	 *         expirado.
	 */
	@Bean
	public Queue paymentExpiredQueue() {
		return new Queue(PAYMENT_EXPIRED_QUEUE, true);
	}

	/**
	 * Declara a fila de erros na inicialização de pagamento no RabbitMQ.
	 *
	 * @return objeto Queue configurado como durável para eventos de erro na
	 *         inicialização de pagamento.
	 */
	@Bean
	public Queue paymentInitializationErrorQueue() {
		return new Queue(PAYMENT_INITIALIZATION_ERROR_QUEUE, true);
	}
}
