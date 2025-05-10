package com.soat.fiap.food.core.api.customer.infrastructure.adapters.out.persistence.repository;

import com.soat.fiap.food.core.api.customer.infrastructure.adapters.out.persistence.entity.CustomerEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório Spring Data JPA para CustomerEntity
 */
@Repository
public interface SpringDataCustomerRepository extends JpaRepository<CustomerEntity, Long> {
    
    /**
     * Busca cliente por DOCUMENT
     * @param document DOCUMENT do cliente
     * @return Optional contendo o cliente ou vazio se não encontrado
     */
    Optional<CustomerEntity> findByDocument(String document);
    
    /**
     * Busca todos os clientes ativos
     * @return Lista de clientes ativos
     */
    List<CustomerEntity> findByActiveTrue();
} 