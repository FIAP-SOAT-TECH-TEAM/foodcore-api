package com.soat.fiap.food.core.api.user.infrastructure.out.persistence.postgres.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.dto.UserDTO;
import com.soat.fiap.food.core.api.user.infrastructure.out.persistence.postgres.entity.RoleEntity;
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
	@Mapping(target = "auditInfo", expression = "java(com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper.shared.AuditInfoMapper.buildAuditInfo(entity.getAuditInfo().getCreatedAt(), entity.getAuditInfo().getUpdatedAt()))")
	User toDomain(UserEntity entity);

	/**
	 * Converte uma lista de entidades JPA para uma lista de entidades de domínio
	 *
	 * @param entities
	 *            Lista de entidades JPA
	 * @return Lista de entidades de domínio
	 */
	@Mapping(target = "auditInfo", expression = "java(com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper.shared.AuditInfoMapper.buildAuditInfo(entities.getAuditInfo().getCreatedAt(), entities.getAuditInfo().getUpdatedAt()))")
	List<User> toDomainList(List<UserEntity> entities);

	/**
	 * Converte uma entidade de domínio para uma entidade JPA
	 *
	 * @param dto
	 *            Entidade de domínio
	 * @return Entidade JPA
	 */
	@Mapping(source = "roleId", target = "role")
	@Mapping(target = "auditInfo", expression = "java(com.soat.fiap.food.core.api.catalog.infrastructure.out.persistence.postgres.mapper.shared.AuditInfoMapper.buildAuditInfo(dto.createdAt(), dto.updatedAt()))")
	UserEntity toEntity(UserDTO dto);

	/**
	 * Converte uma entidade de para um DTO
	 *
	 * @param entity
	 *            Entidade de domínio
	 * @return UserDTO
	 */
	@Mapping(source = "role", target = "roleId") @Mapping(target = "createdAt", source = "auditInfo.createdAt")
	@Mapping(target = "updatedAt", source = "auditInfo.updatedAt")
	UserDTO toDTO(UserEntity entity);

	/**
	 * Converte uma lista de entidades JPA para uma lista de DTOs
	 *
	 * @param entities
	 *            Lista de entidades JPA
	 * @return Lista de DTOs
	 */
	@Mapping(target = "createdAt", source = "auditInfo.createdAt")
	@Mapping(target = "updatedAt", source = "auditInfo.updatedAt")
	List<UserDTO> toDTOList(List<UserEntity> entities);

	// Conversão auxiliar de Long para RoleEntity
	default RoleEntity map(Long roleId) {
		if (roleId == null) {
			return null;
		}
		RoleEntity role = new RoleEntity();
		role.setId(roleId.intValue());
		return role;
	}

	// Conversão auxiliar de RoleEntity para Long
	default Long map(RoleEntity role) {
		return role != null ? role.getId().longValue() : null;
	}
}
