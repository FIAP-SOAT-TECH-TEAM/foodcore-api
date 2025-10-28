package com.soat.fiap.food.core.order.infrastructure.in.event.listener.azsvcbus.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import com.nimbusds.jose.shaded.gson.Gson;
import com.soat.fiap.food.core.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.order.core.interfaceadapters.bff.controller.web.api.UpdateOrderStatusController;
import com.soat.fiap.food.core.order.core.interfaceadapters.dto.events.PaymentInitializationErrorEventDto;
import com.soat.fiap.food.core.order.infrastructure.common.event.azsvcbus.config.ServiceBusConfig;
import com.soat.fiap.food.core.order.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.order.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.order.infrastructure.in.web.api.dto.request.OrderStatusRequest;

import lombok.extern.slf4j.Slf4j;

/**
 * Listener para eventos de erro na inicialização de pagamento.
 * <p>
 * Este listener consome mensagens da fila
 * {@link ServiceBusConfig#PAYMENT_INITIALIZATION_ERROR_QUEUE} e processa
 * eventos {@link PaymentInitializationErrorEventDto}, atualizando o status do
 * pedido como CANCELLED e publicando eventos relacionados.
 */
@Component @Slf4j
public class PaymentInitializationErrorListener {

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
	public PaymentInitializationErrorListener(OrderDataSource orderDataSource, PaymentDataSource paymentDataSource,
			EventPublisherSource eventPublisherSource,
			@Value("${azsvcbus.connection-string}") String connectionString) {

		try (ServiceBusProcessorClient processor = new ServiceBusClientBuilder().connectionString(connectionString)
				.processor()
				.queueName(ServiceBusConfig.PAYMENT_INITIALIZATION_ERROR_QUEUE)
				.receiveMode(ServiceBusReceiveMode.PEEK_LOCK)
				.processMessage(context -> {
					PaymentInitializationErrorEventDto event = gson.fromJson(context.getMessage().getBody().toString(),
							PaymentInitializationErrorEventDto.class);
					handle(event, orderDataSource, paymentDataSource, eventPublisherSource);
				})
				.processError(
						context -> log.error("Erro no listener de inicialização de pagamento", context.getException()))
				.buildProcessorClient()) {

			processor.start();

		} catch (Exception e) {
			log.error("Falha ao iniciar PaymentInitializationErrorListener", e);
		}
	}

	/**
	 * Processa o evento de erro na inicialização do pagamento.
	 *
	 * @param event
	 *            evento de erro na inicialização do pagamento
	 * @param orderDataSource
	 *            fonte de dados de pedidos
	 * @param paymentDataSource
	 *            fonte de dados de pagamentos
	 * @param eventPublisherSource
	 *            publicador de eventos
	 */
	private void handle(PaymentInitializationErrorEventDto event, OrderDataSource orderDataSource,
			PaymentDataSource paymentDataSource, EventPublisherSource eventPublisherSource) {

		log.info("Recebido evento de erro na inicialização do pagamento. Pedido: {}", event.getOrderId());

		var orderUpdateStatusRequest = new OrderStatusRequest(OrderStatus.CANCELLED);
		UpdateOrderStatusController.updateOrderStatus(event.getOrderId(), orderUpdateStatusRequest, orderDataSource,
				paymentDataSource, eventPublisherSource);

		log.info("Pedido {} atualizado para status CANCELLED após erro na inicialização do pagamento",
				event.getOrderId());
	}
}
