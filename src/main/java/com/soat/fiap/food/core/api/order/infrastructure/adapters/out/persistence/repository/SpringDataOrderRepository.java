package com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.repository;

import com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.entity.OrderEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repositório Spring Data JPA para OrderEntity
 */
@Repository
public interface SpringDataOrderRepository extends JpaRepository<OrderEntity, Long> {
    
    /**
     * Busca pedidos por status
     * @param status Status dos pedidos
     * @return Lista de pedidos com o status informado
     */
    List<OrderEntity> findByStatus(OrderEntity.OrderStatusEntity status);
    
    /**
     * Busca pedidos de um cliente específico
     * @param customerId ID do cliente
     * @return Lista de pedidos do cliente
     */
    List<OrderEntity> findByCustomerId(Long customerId);
} 