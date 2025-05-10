package com.soat.fiap.food.core.api.customer.infrastructure.adapters.out.persistence.mapper;

import com.soat.fiap.food.core.api.customer.domain.model.Customer;
import com.soat.fiap.food.core.api.customer.infrastructure.adapters.out.persistence.entity.CustomerEntity;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte entre a entidade de domínio Customer e a entidade JPA CustomerEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerEntityMapper {
    
    /**
     * Converte uma entidade JPA para uma entidade de domínio
     * @param entity Entidade JPA
     * @return Entidade de domínio
     */
    Customer toDomain(CustomerEntity entity);
    
    /**
     * Converte uma lista de entidades JPA para uma lista de entidades de domínio
     * @param entities Lista de entidades JPA
     * @return Lista de entidades de domínio
     */
    List<Customer> toDomainList(List<CustomerEntity> entities);
    
    /**
     * Converte uma entidade de domínio para uma entidade JPA
     * @param domain Entidade de domínio
     * @return Entidade JPA
     */
    CustomerEntity toEntity(Customer domain);
} 