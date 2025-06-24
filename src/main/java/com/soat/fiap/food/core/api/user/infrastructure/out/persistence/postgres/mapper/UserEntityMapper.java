package com.soat.fiap.food.core.api.user.infrastructure.out.persistence.postgres.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.infrastructure.out.persistence.postgres.entity.UserEntity;

/**
 * Mapper que converte entre a entidade de domínio User e a entidade JPA
 * UserEntity
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface UserEntityMapper {

	/**
	 * Converte uma entidade JPA para uma entidade de domínio
	 *
	 * @param entity
	 *            Entidade JPA
	 * @return Entidade de domínio
	 */
	User toDomain(UserEntity entity);

	/**
	 * Converte uma lista de entidades JPA para uma lista de entidades de domínio
	 *
	 * @param entities
	 *            Lista de entidades JPA
	 * @return Lista de entidades de domínio
	 */
	List<User> toDomainList(List<UserEntity> entities);

	/**
	 * Converte uma entidade de domínio para uma entidade JPA
	 *
	 * @param domain
	 *            Entidade de domínio
	 * @return Entidade JPA
	 */
	UserEntity toEntity(User domain);
}
