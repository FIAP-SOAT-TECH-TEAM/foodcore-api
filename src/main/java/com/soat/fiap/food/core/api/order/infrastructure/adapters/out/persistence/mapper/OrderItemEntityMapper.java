package com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.mapper;

import com.soat.fiap.food.core.api.order.domain.model.OrderItem;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.entity.OrderItemEntity;
import com.soat.fiap.food.core.api.catalog.domain.model.Product;
import org.mapstruct.*;

import java.util.List;

/**
 * Mapper que converte entre a entidade de domínio OrderItem e a entidade JPA OrderItemEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface OrderItemEntityMapper {
    
    /**
     * Converte uma entidade JPA para uma entidade de domínio
     * @param entity Entidade JPA
     * @return Entidade de domínio
     */
    @Mapping(target = "product", expression = "java(mapProduct(entity))")
    OrderItem toDomain(OrderItemEntity entity);
    
    /**
     * Converte uma lista de entidades JPA para uma lista de entidades de domínio
     * @param entities Lista de entidades JPA
     * @return Lista de entidades de domínio
     */
    List<OrderItem> toDomainList(List<OrderItemEntity> entities);
    
    /**
     * Converte uma entidade de domínio para uma entidade JPA
     * @param domain Entidade de domínio
     * @return Entidade JPA
     */
    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    @Mapping(target = "order", ignore = true)
    OrderItemEntity toEntity(OrderItem domain);
    
    /**
     * Converte uma lista de entidades de domínio para uma lista de entidades JPA
     * @param domainList Lista de entidades de domínio
     * @return Lista de entidades JPA
     */
    List<OrderItemEntity> toEntityList(List<OrderItem> domainList);
    
    /**
     * Cria um objeto Product com os dados disponíveis na entidade OrderItemEntity
     * @param entity Entidade OrderItemEntity
     * @return Objeto Product preenchido com os dados disponíveis
     */
    default Product mapProduct(OrderItemEntity entity) {
        if (entity == null || entity.getProductId() == null) {
            return null;
        }
        
        return Product.builder()
                .id(entity.getProductId())
                .name(entity.getProductName())
                .build();
    }
} 