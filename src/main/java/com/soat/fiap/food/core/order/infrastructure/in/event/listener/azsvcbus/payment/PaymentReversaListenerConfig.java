package com.soat.fiap.food.core.order.infrastructure.in.event.listener.azsvcbus.payment;

import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusProcessorClient;
import com.azure.messaging.servicebus.models.ServiceBusReceiveMode;
import com.google.gson.Gson;
import com.soat.fiap.food.core.order.core.interfaceadapters.bff.controller.web.api.ChargebackOrderController;
import com.soat.fiap.food.core.order.core.interfaceadapters.dto.events.PaymentReversalEventDto;
import com.soat.fiap.food.core.order.infrastructure.common.event.azsvcbus.config.ServiceBusConfig;
import com.soat.fiap.food.core.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.order.infrastructure.common.source.PaymentDataSource;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Listener responsável por processar eventos de extorno de pagamento.
 *
 * <p>
 * Atualiza o status do pedido e executa o tratamento necessário para eventos
 * relacionados.
 * </p>
 */
@Configuration @Slf4j @RequiredArgsConstructor @Transactional
public class PaymentReversaListenerConfig {

	private final Gson gson;

	@Bean
	public ServiceBusProcessorClient paymentExpiredServiceBusProcessorClient(OrderDataSource orderDataSource,
			PaymentDataSource paymentDataSource,
			@Value("${azsvcbus.connection-string}") String connectionString) {

		return new ServiceBusClientBuilder().connectionString(connectionString)
				.processor()
				.queueName(ServiceBusConfig.PAYMENT_REVERSAL_QUEUE)
				.receiveMode(ServiceBusReceiveMode.PEEK_LOCK)
				.processMessage(context -> {
					PaymentReversalEventDto event = gson.fromJson(context.getMessage().getBody().toString(),
							PaymentReversalEventDto.class);
					handle(event, orderDataSource, paymentDataSource);
				})
				.processError(context -> log.error("Erro ao processar evento de estorno de pagamento", context.getException()))
				.buildProcessorClient();
	}

	private void handle(PaymentReversalEventDto event, OrderDataSource orderDataSource,
						PaymentDataSource paymentDataSource) {

		log.info("Evento de estorno de pagamento recebido: {}", event.getOrderId());

		ChargebackOrderController.chargebackOrder(event.getOrderId(), orderDataSource,
				paymentDataSource);

		log.info("Status do pedido atualizado após estorno de pagamento: {}", event.getOrderId());
	}
}
