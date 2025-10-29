package com.soat.fiap.food.core.order.infrastructure.in.event.listener.azsvcbus.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import com.nimbusds.jose.shaded.gson.Gson;
import com.soat.fiap.food.core.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.order.core.interfaceadapters.bff.controller.web.api.UpdateOrderStatusController;
import com.soat.fiap.food.core.order.core.interfaceadapters.dto.events.PaymentApprovedEventDto;
import com.soat.fiap.food.core.order.infrastructure.common.event.azsvcbus.config.ServiceBusConfig;
import com.soat.fiap.food.core.order.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.order.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.order.infrastructure.in.web.api.dto.request.OrderStatusRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * Listener para eventos de pagamento aprovado.
 * <p>
 * Este listener consome mensagens da fila
 * {@link ServiceBusConfig#PAYMENT_APPROVED_QUEUE} e processa eventos
 * {@link PaymentApprovedEventDto}, atualizando o status do pedido e publicando
 * eventos relacionados.
 */
@Configuration @Slf4j
public class PaymentApprovedListener {

	private final Gson gson = new Gson();

	/**
	 * Construtor do listener.
	 *
	 * @param orderDataSource
	 *            fonte de dados de pedidos
	 * @param paymentDataSource
	 *            fonte de dados de pagamentos
	 * @param eventPublisherSource
	 *            publicador de eventos
	 * @param connectionString
	 *            connection string do Azure Service Bus
	 */
	@Bean
	public ServiceBusProcessorClient paymentApprovedServiceBusProcessorClient(OrderDataSource orderDataSource,
			PaymentDataSource paymentDataSource, EventPublisherSource eventPublisherSource,
			@Value("${azsvcbus.connection-string}") String connectionString) {

		return new ServiceBusClientBuilder().connectionString(connectionString)
				.processor()
				.queueName(ServiceBusConfig.PAYMENT_APPROVED_QUEUE)
				.receiveMode(ServiceBusReceiveMode.PEEK_LOCK)
				.processMessage(context -> {
					PaymentApprovedEventDto event = gson.fromJson(context.getMessage().getBody().toString(),
							PaymentApprovedEventDto.class);
					handle(event, orderDataSource, paymentDataSource, eventPublisherSource);
				})
				.processError(context -> log.error("Erro no listener de pagamento aprovado", context.getException()))
				.buildProcessorClient();
	}

	/**
	 * Processa o evento de pagamento aprovado.
	 *
	 * @param event
	 *            evento de pagamento aprovado
	 * @param orderDataSource
	 *            fonte de dados de pedidos
	 * @param paymentDataSource
	 *            fonte de dados de pagamentos
	 * @param eventPublisherSource
	 *            publicador de eventos
	 */
	private void handle(PaymentApprovedEventDto event, OrderDataSource orderDataSource,
			PaymentDataSource paymentDataSource, EventPublisherSource eventPublisherSource) {

		log.info("Recebido evento de pagamento aprovado para o pedido: {}, valor: {}", event.getOrderId(),
				event.getAmount());

		var orderUpdateStatusRequest = new OrderStatusRequest(OrderStatus.PREPARING);
		UpdateOrderStatusController.updateOrderStatus(event.getOrderId(), orderUpdateStatusRequest, orderDataSource,
				paymentDataSource, eventPublisherSource);

		log.info("Pedido {} atualizado para status PREPARING após pagamento aprovado", event.getOrderId());
	}
}
