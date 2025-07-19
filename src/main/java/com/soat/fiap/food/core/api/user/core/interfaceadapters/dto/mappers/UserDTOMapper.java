package com.soat.fiap.food.core.api.user.core.interfaceadapters.dto.mappers;

import java.util.List;
import java.util.stream.Collectors;

import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.dto.UserDTO;

/**
 * Mapper manual para conversão entre User e UserDTO.
 */
public class UserDTOMapper {

	/**
	 * Converte um UserDTO em uma entidade de domínio User.
	 *
	 * @param dto
	 *            DTO de entrada
	 * @return Entidade de domínio User
	 */
	public static User toDomain(UserDTO dto) {
		return User.fromDTO(dto);
	}

	/**
	 * Converte uma entidade de domínio User em um UserDTO.
	 *
	 * @param user
	 *            Entidade de domínio
	 * @return DTO equivalente
	 */
	public static UserDTO toDTO(User user) {
		return new UserDTO(user.getId(), user.getName(), user.getUsername(), user.getEmail(), user.getPassword(),
				user.getDocument(), user.getRole() != null ? user.getRole().getId() : null, user.isGuest(),
				user.isActive(), user.getAuditInfo().getCreatedAt(), user.getAuditInfo().getUpdatedAt());
	}

	/**
	 * Converte uma lista de UserDTOs em uma lista de entidades de domínio User.
	 *
	 * @param dtoList
	 *            Lista de DTOs
	 * @return Lista de entidades de domínio User
	 */
	public static List<User> toDomainList(List<UserDTO> dtoList) {
		return dtoList.stream().map(UserDTOMapper::toDomain).collect(Collectors.toList());
	}
}
