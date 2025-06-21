package com.soat.fiap.food.core.api.payment.infrastructure.in.event.listener;

import com.soat.fiap.food.core.api.order.core.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.controller.web.api.InitializePaymentController;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.AcquirerSource;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Ouvinte de eventos de pedido no módulo de pagamento
 */
@Component
@Slf4j
public class PaymentOrderEventListener {
    
    private final PaymentDataSource paymentDataSource;
    private final AcquirerSource acquirerSource;
    private final EventPublisherSource eventPublisherSource;

    public PaymentOrderEventListener(PaymentDataSource paymentDataSource, AcquirerSource acquirerSource, EventPublisherSource eventPublisherSource) {
        this.paymentDataSource = paymentDataSource;
        this.acquirerSource = acquirerSource;
        this.eventPublisherSource = eventPublisherSource;
    }

    /**
     * Processa o evento de pedido criado iniciando um processo de pagamento
     * 
     * @param event Evento de pedido criado
     */
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Módulo Payment: Iniciando pagamento para o pedido: {} com valor total: {}",
                event.getId(), event.getTotalAmount());
        
        InitializePaymentController.initializePayment(event, paymentDataSource, acquirerSource, eventPublisherSource);
    }
} 