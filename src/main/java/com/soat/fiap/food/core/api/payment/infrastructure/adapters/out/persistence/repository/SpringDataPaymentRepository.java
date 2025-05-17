package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.persistence.repository;

import com.soat.fiap.food.core.api.order.domain.vo.OrderPaymentStatus;
import com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.persistence.entity.PaymentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório Spring Data JPA para a entidade PaymentEntity
 */
@Repository
public interface SpringDataPaymentRepository extends JpaRepository<PaymentEntity, Long> {
    
    /**
     * Busca uma entidade de pagamento pelo ID externo
     */
    Optional<PaymentEntity> findByExternalId(String externalId);
    
    /**
     * Busca uma entidade de pagamento pelo ID do pedido
     */
    Optional<PaymentEntity> findByOrderId(Long orderId);
    
    /**
     * Lista todas as entidades de pagamento com determinado status
     */
    List<PaymentEntity> findByStatus(OrderPaymentStatus status);
} 