package com.soat.fiap.food.core.api.shared.fixtures;

import java.time.LocalDateTime;

import com.soat.fiap.food.core.api.user.core.domain.model.Role;
import com.soat.fiap.food.core.api.user.core.domain.model.User;

/**
 * Fixture para criação de objetos User para testes unitários
 */
public class UserFixture {

	public static User createValidUser() {
		return new User(false, "João Silva", "joao.silva", "joao@email.com", "senha123456", "12345678901",
				Role.defaultRole());
	}

	public static User createGuestUser() {
		return new User(true, "Usuário Convidado", null, null, null, null, Role.guestRole());
	}

	public static User createUserWithEmail() {
		return new User(false, "Maria Santos", null, "maria@email.com", "senha123456", null, Role.defaultRole());
	}

	public static User createUserWithDocument() {
		return new User(false, "Pedro Oliveira", null, null, "senha123456", "98765432100", Role.defaultRole());
	}

	public static User createUserWithUsername() {
		return new User(false, "Ana Costa", "ana.costa", null, "senha123456", null, Role.defaultRole());
	}

	public static User createUserWithCreationDate(LocalDateTime createdAt) {
		var user = new User(false, "João Silva", "joao.silva", "joao@email.com", "senha123456", "12345678901",
				Role.defaultRole()) {
			@Override
			public LocalDateTime getCreatedAt() {
				return createdAt;
			}
		};

		return user;
	}
}
