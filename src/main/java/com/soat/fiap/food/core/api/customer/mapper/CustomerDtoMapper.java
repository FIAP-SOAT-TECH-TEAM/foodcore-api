package com.soat.fiap.food.core.api.customer.mapper;

import com.soat.fiap.food.core.api.customer.domain.model.Customer;
import com.soat.fiap.food.core.api.customer.infrastructure.adapters.in.dto.request.CustomerRequest;
import com.soat.fiap.food.core.api.customer.infrastructure.adapters.in.dto.response.CustomerResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;

import java.util.List;

/**
 * Mapper que converte entre DTOs e entidades de domínio para Customer
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface CustomerDtoMapper {
    
    /**
     * Converte entidade de domínio para DTO de resposta
     * @param customer Entidade de domínio
     * @return DTO de resposta
     */
    CustomerResponse toResponse(Customer customer);
    
    /**
     * Converte lista de entidades de domínio para lista de DTOs de resposta
     * @param customers Lista de entidades de domínio
     * @return Lista de DTOs de resposta
     */
    List<CustomerResponse> toResponseList(List<Customer> customers);
    
    /**
     * Converte DTO de requisição para entidade de domínio
     * @param request DTO de requisição
     * @return Entidade de domínio
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", constant = "true")
    Customer toDomain(CustomerRequest request);
    
    /**
     * Atualiza uma entidade de domínio com os dados de um DTO de requisição
     * @param request DTO de requisição com os dados atualizados
     * @param customer Entidade de domínio a ser atualizada
     */
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "active", ignore = true)
    void updateDomainFromRequest(CustomerRequest request, @MappingTarget Customer customer);
} 