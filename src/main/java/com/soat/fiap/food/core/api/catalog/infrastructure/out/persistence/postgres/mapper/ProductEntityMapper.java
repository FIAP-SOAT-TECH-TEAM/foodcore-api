package com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper;

import java.util.List;

import org.mapstruct.Context;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.ProductDTO;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.entity.ProductEntity;
import com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper.shared.ImageURLMapper;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.DoIgnore;

/**
 * Mapper que converte entre a entidade de domínio Product e a entidade JPA
 * ProductEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE, uses = {StockEntityMapper.class,
		ImageURLMapper.class})
public interface ProductEntityMapper {

	/**
	 * Converte uma entidade JPA para um DTO.
	 *
	 * @param entity
	 *            Entidade JPA
	 * @return DTO correspondente
	 */
	@Mapping(target = "imageUrl", source = "imageUrl", qualifiedByName = "mapImageUrlToString")
	ProductDTO toDTO(ProductEntity entity);

	/**
	 * Converte uma entidade JPA para uma entidade de domínio
	 *
	 * @param entity
	 *            Entidade JPA
	 * @param cycleAvoidingMappingContext
	 *            Contexto para evitar ciclos
	 * @return Entidade de domínio
	 */
	@Mapping(target = "stock", source = "stock")
	Product toDomain(ProductEntity entity, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	/**
	 * Converte uma lista de entidades JPA para uma lista de entidades de domínio
	 *
	 * @param entities
	 *            Lista de entidades JPA
	 * @param cycleAvoidingMappingContext
	 *            Contexto para evitar ciclos
	 * @return Lista de entidades de domínio
	 */
	@Mapping(target = "stock", source = "stock")
	List<Product> toDomainList(List<ProductEntity> entities,
			@Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	/**
	 * Converte uma entidade de domínio para uma entidade JPA
	 *
	 * @param dto
	 *            DTO do produto
	 * @return Entidade JPA
	 */
	@Mapping(target = "imageUrl", source = "imageUrl", qualifiedByName = "mapStringToImageUrl")
	@Mapping(target = "stock", source = "stock")
	ProductEntity toEntity(ProductDTO dto, @Context CycleAvoidingMappingContext cycleAvoidingMappingContext);

	@DoIgnore @Mapping(target = "stock", source = "stock")
	default Product toDomain(ProductEntity entity) {
		return toDomain(entity, new CycleAvoidingMappingContext());
	}

	@DoIgnore @Mapping(target = "stock", source = "stock")
	default List<Product> toDomainList(List<ProductEntity> entities) {
		return toDomainList(entities, new CycleAvoidingMappingContext());
	}

	@DoIgnore @Mapping(target = "imageUrl", source = "imageUrl", qualifiedByName = "mapStringToImageUrl")
	@Mapping(target = "stock", source = "stock")
	default ProductEntity toEntity(ProductDTO dto) {
		return toEntity(dto, new CycleAvoidingMappingContext());
	}
}
