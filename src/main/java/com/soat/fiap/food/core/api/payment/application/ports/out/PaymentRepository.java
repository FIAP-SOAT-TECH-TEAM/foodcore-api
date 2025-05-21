package com.soat.fiap.food.core.api.payment.application.ports.out;

import com.soat.fiap.food.core.api.payment.domain.model.Payment;
import com.soat.fiap.food.core.api.order.domain.vo.OrderPaymentStatus;

import java.util.List;
import java.util.Optional;

/**
 * Interface que define as operações de persistência para a entidade Payment
 */
public interface PaymentRepository {
    
    /**
     * Salva um pagamento no repositório
     * 
     * @param payment Pagamento a ser salvo
     * @return Pagamento salvo com ID gerado
     */
    Payment save(Payment payment);
    
    /**
     * Busca um pagamento pelo ID
     * 
     * @param id ID do pagamento
     * @return Pagamento encontrado ou vazio
     */
    Optional<Payment> findById(Long id);
    
    /**
     * Busca um pagamento pelo ID externo (usado para pagamentos de terceiros como Mercado Pago)
     * 
     * @param externalId ID externo do pagamento
     * @return Pagamento encontrado ou vazio
     */
    Optional<Payment> findByExternalId(String externalId);
    
    /**
     * Busca um pagamento pelo ID do pedido
     * 
     * @param orderId ID do pedido
     * @return Pagamento encontrado ou vazio
     */
    Optional<Payment> findByOrderId(Long orderId);
    
    /**
     * Lista todos os pagamentos com determinado status
     * 
     * @param status Status dos pagamentos
     * @return Lista de pagamentos
     */
    List<Payment> findByStatus(OrderPaymentStatus status);
} 