package com.soat.fiap.food.core.api.order.infrastructure.in.event.listener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.controller.web.api.UpdateOrderStatusController;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.request.OrderStatusRequest;
import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentApprovedEvent;
import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentExpiredEvent;
import com.soat.fiap.food.core.api.payment.core.domain.events.PaymentInitializationErrorEvent;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Ouvinte de eventos de pagamento no módulo de pedidos
 */
@Component @Slf4j
public class OrderPaymentEventListener {

	private final OrderDataSource orderDataSource;
	private final PaymentDataSource paymentDataSource;
	private final EventPublisherSource eventPublisherSource;

	public OrderPaymentEventListener(OrderDataSource orderDataSource, PaymentDataSource paymentDataSource,
			EventPublisherSource eventPublisherSource) {
		this.orderDataSource = orderDataSource;
		this.paymentDataSource = paymentDataSource;
		this.eventPublisherSource = eventPublisherSource;
	}

	/**
	 * Processa o evento de pagamento aprovado
	 *
	 * @param event
	 *            Evento de pagamento aprovado
	 */
	@EventListener @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handlePaymentApprovedEvent(PaymentApprovedEvent event) {
		log.info("Módulo Order: Recebido evento de pagamento aprovado para o pedido: {}, valor: {}", event.getOrderId(),
				event.getAmount());

		var orderUpddateStatusRequest = new OrderStatusRequest(OrderStatus.PREPARING);
		UpdateOrderStatusController.updateOrderStatus(event.getOrderId(), orderUpddateStatusRequest, orderDataSource,
				paymentDataSource, eventPublisherSource);

		log.info("Pedido {} atualizado para status PREPARING após pagamento aprovado", event.getOrderId());
	}

	/**
	 * Processa o evento de erro na inicialização do pagamento
	 *
	 * @param event
	 *            Evento de erro na inicialização do pagamento
	 */
	@EventListener @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handlePaymentInitializationErrorEvent(PaymentInitializationErrorEvent event) {
		log.info("Módulo Order: Recebido evento de erro na inicialização do pagamento. Pedido: {}", event.getOrderId());

		var orderUpddateStatusRequest = new OrderStatusRequest(OrderStatus.CANCELLED);
		UpdateOrderStatusController.updateOrderStatus(event.getOrderId(), orderUpddateStatusRequest, orderDataSource,
				paymentDataSource, eventPublisherSource);
	}

	/**
	 * Processa eventos de pagamento expirado
	 *
	 * @param event
	 *            Evento de pagamento expirado
	 */
	@EventListener @Transactional(propagation = Propagation.REQUIRES_NEW)
	public void handlePaymentExpiredEvent(PaymentExpiredEvent event) {
		log.info("Módulo Order: Recebido evento de pagamento expirado. Pedido: {}, Pagamento: {}", event.getOrderId(),
				event.getPaymentId());

		var orderUpddateStatusRequest = new OrderStatusRequest(OrderStatus.CANCELLED);
		UpdateOrderStatusController.updateOrderStatus(event.getOrderId(), orderUpddateStatusRequest, orderDataSource,
				paymentDataSource, eventPublisherSource);
	}
}
