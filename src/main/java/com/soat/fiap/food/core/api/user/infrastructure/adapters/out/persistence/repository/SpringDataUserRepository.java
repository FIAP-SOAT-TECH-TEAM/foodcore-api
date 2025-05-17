package com.soat.fiap.food.core.api.user.infrastructure.adapters.out.persistence.repository;

import com.soat.fiap.food.core.api.user.infrastructure.adapters.out.persistence.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repositório Spring Data JPA para UserEntity
 */
@Repository
public interface SpringDataUserRepository extends JpaRepository<UserEntity, Long> {
    
    /**
     * Busca usuário por DOCUMENT
     * @param document DOCUMENT do usuário
     * @return Optional contendo o usuário ou vazio se não encontrado
     */
    Optional<UserEntity> findByDocument(String document);
    
    /**
     * Busca todos os usuários ativos
     * @return Lista de usuários ativos
     */
    List<UserEntity> findByActiveTrue();
} 