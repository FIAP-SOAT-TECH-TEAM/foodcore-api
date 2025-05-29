package com.soat.fiap.food.core.api.order.infrastructure.adapters.in.eventlistener;

import com.soat.fiap.food.core.api.order.application.ports.in.OrderUseCase;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.payment.domain.events.PaymentApprovedEvent;
import com.soat.fiap.food.core.api.payment.domain.events.PaymentInitializationErrorEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * Ouvinte de eventos de pagamento no módulo de pedidos
 */
@Component
@Slf4j
public class PaymentEventListener {
    
    private final OrderUseCase orderUseCase;
    
    public PaymentEventListener(OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
    }
    
    /**
     * Processa o evento de pagamento aprovado
     * 
     * @param event Evento de pagamento aprovado
     */
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlePaymentApprovedEvent(PaymentApprovedEvent event) {
        log.info("Módulo Order: Recebido evento de pagamento aprovado para o pedido: {}, valor: {}",
                event.getOrderId(), event.getAmount());
        
          orderUseCase.updateOrderStatusToPreparing(event.getOrderId());

          log.info("Pedido {} atualizado para status PREPARING após pagamento aprovado", event.getOrderId());
    }

    /**
     * Processa o evento de erro na inicialização do pagamento
     *
     * @param event Evento de erro na inicialização do pagamento
     */
    @EventListener
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void handlePaymentInitializationErrorEvent(PaymentInitializationErrorEvent event) {
        log.info("Módulo Order: Recebido evento de erro na inicialização do pagamento. Pedido: {}", event.getOrderId());

        orderUseCase.updateOrderStatusToCancelled(event.getOrderId());
    }
} 