package com.soat.fiap.food.core.api.payment.infrastructure.adapters.in.eventlistener;

import com.soat.fiap.food.core.api.order.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.payment.application.ports.in.PaymentUseCase;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ouvinte de eventos de pedido no módulo de pagamento
 */
@Component
public class OrderEventListener {
    
    private static final Logger logger = LoggerFactory.getLogger(OrderEventListener.class);
    
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
        logger.info("Módulo Payment: Iniciando pagamento para o pedido: {} com valor total: {}",
                event.getOrderId(), event.getTotalAmount());
        
        // TODO: iniciar o processo de pagamento
        // paymentUseCase.initializePayment(event.getOrderId(), event.getTotalAmount());
        
        // Por enquanto, apenas logamos a chegada do evento
        logger.info("Pagamento para pedido {} seria iniciado no status: {}", 
                event.getOrderId(), event.getStatus());
    }
} 