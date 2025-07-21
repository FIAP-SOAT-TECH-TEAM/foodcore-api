package com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.presenter.web.api;

import java.util.List;

import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.response.UserResponse;

/**
 * Presenter responsável por converter objetos do domínio {@link User} em
 * objetos de resposta {@link UserResponse} utilizados na camada de API web
 * (web.api).
 */
public class UserPresenter {

	/**
	 * Converte uma instância da entidade {@link User} para um {@link UserResponse}.
	 *
	 * @param user
	 *            A entidade de domínio {@link User} a ser convertida.
	 * @return Um DTO {@link UserResponse} com os dados formatados para resposta
	 *         HTTP.
	 */
	public static UserResponse toUserResponse(User user) {
		if (user == null) {
			return null;
		}

		UserResponse response = new UserResponse();
		response.setId(user.getId());
		response.setName(user.getName());
		response.setUsername(user.getUsername());
		response.setEmail(user.getEmail());
		response.setDocument(user.getDocument());
		response.setActive(user.isActive());
		response.setRole(user.getRole() != null ? user.getRole().getId() : null);
		response.setCreatedAt(user.getCreatedAt());
		response.setUpdatedAt(user.getAuditInfo() != null ? user.getAuditInfo().getUpdatedAt() : null);
		response.setToken(user.getToken());

		return response;
	}

	/**
	 * Converte uma lista de instâncias da entidade {@link User} para uma lista de
	 * {@link UserResponse}, utilizada na exposição de dados via API REST (web.api).
	 *
	 * @param users
	 *            A lista de entidades de domínio {@link User} a serem convertidas.
	 * @return Uma lista de DTOs {@link UserResponse} com os dados dos usuários
	 *         formatados para resposta HTTP.
	 */
	public static List<UserResponse> toListUserResponse(List<User> users) {
		if (users == null) {
			return List.of();
		}

		return users.stream().map(UserPresenter::toUserResponse).toList();
	}
}
