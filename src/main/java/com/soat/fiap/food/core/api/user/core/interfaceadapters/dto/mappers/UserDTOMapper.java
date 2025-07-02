package com.soat.fiap.food.core.api.user.core.interfaceadapters.dto.mappers;

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
				user.isActive());
	}
}
