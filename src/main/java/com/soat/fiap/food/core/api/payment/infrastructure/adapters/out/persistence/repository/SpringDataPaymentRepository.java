package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.persistence.repository;

import com.soat.fiap.food.core.api.payment.domain.vo.PaymentStatus;
import com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repositório Spring Data JPA para a entidade PaymentEntity
 */
@Repository
public interface SpringDataPaymentRepository extends JpaRepository<PaymentEntity, Long> {
//
//    /**
//     * Busca uma entidade de pagamento pelo ID externo
//     */
//    Optional<PaymentEntity> findByExternalId(String externalId);
//
    /**
     * Busca o último pagamento inserido para um determinado pedido
     */
    Optional<PaymentEntity> findTopByOrderIdOrderByIdDesc(Long orderId);

    /**
     * Verifica se já existe um pagamento para o pedido
     */
    boolean existsByOrderId(Long orderId);

    /**
     * Busca pagamentos expirados com um determinado status
     */
    List<PaymentEntity> findByStatusAndExpiresInBefore(PaymentStatus status, OffsetDateTime now);
//
//    /**
//     * Lista todas as entidades de pagamento com determinado status
//     */
//    List<PaymentEntity> findByStatus(OrderPaymentStatus status);
} 