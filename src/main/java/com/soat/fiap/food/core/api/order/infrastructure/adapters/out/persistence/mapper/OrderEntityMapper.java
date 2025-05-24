package com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.mapper;

import com.soat.fiap.food.core.api.order.domain.model.Order;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.entity.OrderEntity;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.entity.OrderEntity.OrderStatusEntity;
import com.soat.fiap.food.core.api.user.domain.model.User;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper que converte entre a entidade de domínio Order e a entidade JPA OrderEntity
 */
@Mapper(componentModel = "spring", 
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        uses = {OrderItemEntityMapper.class})
public interface OrderEntityMapper {
    
    /**
     * Converte uma entidade JPA para uma entidade de domínio
     * @param entity Entidade JPA
     * @return Entidade de domínio
     */
    @Mapping(target = "userId", expression = "java(mapUser(entity))")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToDomainValue")
    Order toDomain(OrderEntity entity);
    
    /**
     * Converte uma lista de entidades JPA para uma lista de entidades de domínio
     * @param entities Lista de entidades JPA
     * @return Lista de entidades de domínio
     */
    List<Order> toDomainList(List<OrderEntity> entities);
    
    /**
     * Converte uma entidade de domínio para uma entidade JPA
     * @param domain Entidade de domínio
     * @return Entidade JPA
     */
    @Mapping(target = "userId", source = "userId.id")
    @Mapping(target = "status", source = "status", qualifiedByName = "statusToDatabaseValue")
    OrderEntity toEntity(Order domain);
    
    /**
     * Cria um objeto User com ID a partir da entidade OrderEntity
     * Este é um mapeamento parcial usado apenas para preservar a referência do cliente
     */
    default User mapUser(OrderEntity entity) {
        if (entity == null || entity.getUserId() == null) {
            return null;
        }
        
        return User.builder()
                .id(entity.getUserId())
                .build();
    }
    
    /**
     * Converte um OrderStatus do domínio para um OrderStatusEntity do JPA
     */
    @Named("statusToDatabaseValue")
    default OrderStatusEntity statusToDatabaseValue(OrderStatus status) {
        if (status == null) {
            return null;
        }
        
        switch (status) {
            case RECEIVED:
                return OrderStatusEntity.RECEIVED;
            case PREPARING:
                return OrderStatusEntity.PREPARING;
            case READY:
                return OrderStatusEntity.READY;
            case COMPLETED:
                return OrderStatusEntity.COMPLETED;
            case CANCELLED:
                return OrderStatusEntity.CANCELLED;
            case WAITING_PAYMENT:
                return OrderStatusEntity.WAITING_PAYMENT;
            default:
                throw new IllegalArgumentException("Status desconhecido: " + status);
        }
    }
    
    /**
     * Converte um OrderStatusEntity do JPA para um OrderStatus do domínio
     */
    @Named("statusToDomainValue")
    default OrderStatus statusToDomainValue(OrderStatusEntity statusEntity) {
        if (statusEntity == null) {
            return null;
        }
        
        switch (statusEntity) {
            case RECEIVED:
                return OrderStatus.RECEIVED;
            case PREPARING:
                return OrderStatus.PREPARING;
            case READY:
                return OrderStatus.READY;
            case COMPLETED:
                return OrderStatus.COMPLETED;
            case CANCELLED:
                return OrderStatus.CANCELLED;
            case WAITING_PAYMENT:
                return OrderStatus.WAITING_PAYMENT;
            default:
                throw new IllegalArgumentException("Status desconhecido: " + statusEntity);
        }
    }
} 