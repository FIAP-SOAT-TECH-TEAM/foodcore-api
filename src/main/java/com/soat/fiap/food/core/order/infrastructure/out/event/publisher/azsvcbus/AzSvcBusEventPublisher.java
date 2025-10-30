package com.soat.fiap.food.core.order.infrastructure.out.event.publisher.azsvcbus;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusMessage;
import com.azure.messaging.servicebus.ServiceBusSenderClient;
import com.google.gson.Gson;
import com.soat.fiap.food.core.order.core.interfaceadapters.dto.events.OrderCanceledEventDto;
import com.soat.fiap.food.core.order.core.interfaceadapters.dto.events.OrderCreatedEventDto;
import com.soat.fiap.food.core.order.infrastructure.common.event.azsvcbus.config.ServiceBusConfig;
import com.soat.fiap.food.core.order.infrastructure.common.source.EventPublisherSource;

import jakarta.annotation.PreDestroy;
import lombok.extern.slf4j.Slf4j;

/**
 * Implementação do {@link EventPublisherSource} usando Azure Service Bus.
 * <p>
 * Esta classe envia eventos de domínio para tópicos e filas do Azure Service
 * Bus correspondentes. Cada método publica um tipo de evento específico.
 * </p>
 */
@Slf4j @Component
public class AzSvcBusEventPublisher implements EventPublisherSource {

	private final ServiceBusSenderClient orderCreatedSender;
	private final ServiceBusSenderClient orderCanceledSender;
	private final Gson gson;

	/**
	 * Construtor que inicializa os clients do Azure Service Bus usando a connection
	 * string.
	 *
	 * @param connectionString
	 *            Connection string do Azure Service Bus, lida do application.yaml
	 */
	public AzSvcBusEventPublisher(@Value("${azsvcbus.connection-string}") String connectionString, Gson gson) {

		this.orderCreatedSender = new ServiceBusClientBuilder().connectionString(connectionString)
				.sender()
				.topicName(ServiceBusConfig.ORDER_CREATED_TOPIC)
				.buildClient();

		this.orderCanceledSender = new ServiceBusClientBuilder().connectionString(connectionString)
				.sender()
				.queueName(ServiceBusConfig.ORDER_CANCELED_QUEUE)
				.buildClient();

		this.gson = gson;
	}

	/**
	 * Publica um evento de pedido criado no tópico correspondente do Azure Service
	 * Bus.
	 *
	 * @param event
	 *            Evento de pedido criado
	 */
	@Override
	public void publishOrderCreatedEvent(OrderCreatedEventDto event) {
		try {
			orderCreatedSender.sendMessage(new ServiceBusMessage(gson.toJson(event)));
			log.info("Evento de pedido criado publicado com sucesso: {}", event);
		} catch (Exception ex) {
			log.error("Erro ao publicar evento de pedido criado", ex);
		}
	}

	/**
	 * Publica um evento de pedido cancelado na fila correspondente do Azure Service
	 * Bus.
	 *
	 * @param event
	 *            Evento de pedido cancelado
	 */
	@Override
	public void publishOrderCanceledEvent(OrderCanceledEventDto event) {
		try {
			orderCanceledSender.sendMessage(new ServiceBusMessage(gson.toJson(event)));
			log.info("Evento de pedido cancelado publicado com sucesso: {}", event);
		} catch (Exception ex) {
			log.error("Erro ao publicar evento de pedido cancelado", ex);
		}
	}

	/**
	 * Fecha todos os clients do Azure Service Bus ao destruir o bean.
	 */
	@PreDestroy
	public void close() {
		log.info("Fechando clients do Azure Service Bus...");
		if (orderCreatedSender != null)
			orderCreatedSender.close();
		if (orderCanceledSender != null)
			orderCanceledSender.close();
	}
}
