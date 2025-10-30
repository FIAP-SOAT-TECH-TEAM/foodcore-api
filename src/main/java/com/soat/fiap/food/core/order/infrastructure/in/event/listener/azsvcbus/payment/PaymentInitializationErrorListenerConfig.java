package com.soat.fiap.food.core.order.infrastructure.in.event.listener.azsvcbus.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.models.SubQueue;
import com.google.gson.Gson;
import com.soat.fiap.food.core.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.order.core.interfaceadapters.bff.controller.web.api.UpdateOrderStatusController;
import com.soat.fiap.food.core.order.core.interfaceadapters.dto.events.OrderCreatedEventDto;
import com.soat.fiap.food.core.order.infrastructure.common.event.azsvcbus.config.ServiceBusConfig;
import com.soat.fiap.food.core.order.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.order.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.order.infrastructure.in.web.api.dto.request.OrderStatusRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Listener responsável por processar eventos de erro na inicialização de
 * pagamento.
 *
 * <p>
 * Atualiza o status do pedido e executa o tratamento necessário para eventos
 * relacionados.
 * </p>
 * @see <a href="https://learn.microsoft.com/pt-br/java/api/overview/azure/messaging-servicebus-readme?view=azure-java-stable#create-a-dead-letter-queue-receiver">Create a dead-letter queue Receiver - Azure Service Bus</a>
 */
@Configuration @Slf4j @RequiredArgsConstructor
public class PaymentInitializationErrorListenerConfig {

	private final Gson gson;

	@Bean
	public ServiceBusProcessorClient paymentInitializationErrorServiceBusProcessorClient(
			OrderDataSource orderDataSource, PaymentDataSource paymentDataSource,
			EventPublisherSource eventPublisherSource,
			@Value("${azsvcbus.connection-string}") String connectionString) {

		return new ServiceBusClientBuilder().connectionString(connectionString)
				.processor()
				.topicName(ServiceBusConfig.ORDER_CREATED_TOPIC)
				.subscriptionName(ServiceBusConfig.PAYMENT_ORDER_CREATED_TOPIC_SUBSCRIPTION)
				.subQueue(SubQueue.DEAD_LETTER_QUEUE)
				.processMessage(context -> {
					OrderCreatedEventDto event = gson.fromJson(context.getMessage().getBody().toString(),
							OrderCreatedEventDto.class);
					handle(event, orderDataSource, paymentDataSource, eventPublisherSource);
				})
				.processError(context -> log.error("Erro ao processar erro de inicialização de pagamento",
						context.getException()))
				.buildProcessorClient();
	}

	private void handle(OrderCreatedEventDto event, OrderDataSource orderDataSource,
			PaymentDataSource paymentDataSource, EventPublisherSource eventPublisherSource) {

		log.info("Evento de erro na inicialização do pagamento recebido: {}", event.getId());

		var orderUpdateStatusRequest = new OrderStatusRequest(OrderStatus.CANCELLED);
		UpdateOrderStatusController.updateOrderStatus(event.getId(), orderUpdateStatusRequest, orderDataSource,
				paymentDataSource, eventPublisherSource);

		log.info("Status do pedido atualizado após erro na inicialização do pagamento: {}", event.getId());
	}
}
