package com.soat.fiap.food.core.api.payment.infrastructure.adapters.in.eventlistener;

import com.soat.fiap.food.core.api.order.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.payment.application.ports.in.PaymentUseCase;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ouvinte de eventos de pedido no módulo de pagamento
 */
@Component
@Slf4j
public class OrderEventListener {
    
    private final PaymentUseCase paymentUseCase;
    
    public OrderEventListener(PaymentUseCase paymentUseCase) {
        this.paymentUseCase = paymentUseCase;
    }
    
    /**
     * Processa o evento de pedido criado iniciando um processo de pagamento
     * 
     * @param event Evento de pedido criado
     */
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handleOrderCreatedEvent(OrderCreatedEvent event) {
        log.info("Módulo Payment: Iniciando pagamento para o pedido: {} com valor total: {}",
                event.getId(), event.getTotalAmount());
        
        paymentUseCase.initializePayment(event);

    }
} 