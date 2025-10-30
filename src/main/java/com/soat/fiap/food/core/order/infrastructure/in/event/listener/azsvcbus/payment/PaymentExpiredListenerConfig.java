package com.soat.fiap.food.core.order.infrastructure.in.event.listener.azsvcbus.payment;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import com.google.gson.Gson;
import com.soat.fiap.food.core.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.order.core.interfaceadapters.bff.controller.web.api.UpdateOrderStatusController;
import com.soat.fiap.food.core.order.core.interfaceadapters.dto.events.PaymentExpiredEventDto;
import com.soat.fiap.food.core.order.infrastructure.common.event.azsvcbus.config.ServiceBusConfig;
import com.soat.fiap.food.core.order.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.order.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.order.infrastructure.in.web.api.dto.request.OrderStatusRequest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * Listener responsável por processar eventos de pagamento expirado.
 *
 * <p>
 * Atualiza o status do pedido e executa o tratamento necessário para eventos
 * relacionados.
 * </p>
 */
@Configuration @Slf4j @RequiredArgsConstructor
public class PaymentExpiredListenerConfig {

	private final Gson gson;

	@Bean
	public ServiceBusProcessorClient paymentExpiredServiceBusProcessorClient(OrderDataSource orderDataSource,
			PaymentDataSource paymentDataSource, EventPublisherSource eventPublisherSource,
			@Value("${azsvcbus.connection-string}") String connectionString) {

		return new ServiceBusClientBuilder().connectionString(connectionString)
				.processor()
				.queueName(ServiceBusConfig.PAYMENT_EXPIRED_QUEUE)
				.receiveMode(ServiceBusReceiveMode.PEEK_LOCK)
				.processMessage(context -> {
					PaymentExpiredEventDto event = gson.fromJson(context.getMessage().getBody().toString(),
							PaymentExpiredEventDto.class);
					handle(event, orderDataSource, paymentDataSource, eventPublisherSource);
				})
				.processError(context -> log.error("Erro ao processar pagamento expirado", context.getException()))
				.buildProcessorClient();
	}

	private void handle(PaymentExpiredEventDto event, OrderDataSource orderDataSource,
			PaymentDataSource paymentDataSource, EventPublisherSource eventPublisherSource) {

		log.info("Evento de pagamento expirado recebido: {}", event.getOrderId());

		var orderUpdateStatusRequest = new OrderStatusRequest(OrderStatus.CANCELLED);
		UpdateOrderStatusController.updateOrderStatus(event.getOrderId(), orderUpdateStatusRequest, orderDataSource,
				paymentDataSource, eventPublisherSource);

		log.info("Status do pedido atualizado após pagamento expirado: {}", event.getOrderId());
	}
}
