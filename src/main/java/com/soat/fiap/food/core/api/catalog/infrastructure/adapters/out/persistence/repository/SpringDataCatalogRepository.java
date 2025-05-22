package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.repository;

import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.entity.CatalogEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

/**
 * Repositório Spring Data JPA para CatalogEntity
 */
@Repository
public interface SpringDataCatalogRepository extends JpaRepository<CatalogEntity, Long> {

    Optional<CatalogEntity> findByName(String name);

} 