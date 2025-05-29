package com.soat.fiap.food.core.api.payment.domain.ports.out;

import com.soat.fiap.food.core.api.payment.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.domain.vo.PaymentStatus;

import java.time.OffsetDateTime;
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
     * Busca o último pagamento inserido para um determinado pedido
     *
     * @param orderId ID do pedido
     * @return Pagamento encontrado ou vazio
     */
    Optional<Payment> findTopByOrderIdOrderByIdDesc(Long orderId);

    /**
     * Busca pagamentos expirados com um determinado status
     * @param status status do pagamento
     * @param now {@code OffsetDateTime} com o horário atual
     * @return Pagamento encontrado ou vazio
     */
    List<Payment> findByStatusAndExpiresInBefore(PaymentStatus status, OffsetDateTime now);

    /**
     * Verifica se já existe um pagamento para o pedido
     */
    boolean existsByOrderId(Long orderId);
} 