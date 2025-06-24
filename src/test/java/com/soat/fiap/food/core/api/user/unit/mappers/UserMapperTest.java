package com.soat.fiap.food.core.api.user.unit.mappers;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.api.user.core.application.inputs.UserInput;
import com.soat.fiap.food.core.api.user.core.application.inputs.mappers.UserMapper;
import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.request.UserRequest;

@DisplayName("UserMapper - Testes Unitários")
class UserMapperTest {

	@Test @DisplayName("Deve mapear UserRequest para UserInput com sucesso")
	void shouldMapUserRequestToInput() {
		// Arrange
		UserRequest request = new UserRequest();
		request.setGuest(false);
		request.setName("João Silva");
		request.setUsername("joao.silva");
		request.setEmail("joao@email.com");
		request.setPassword("senha123");
		request.setDocument("12345678901");

		// Act
		UserInput result = UserMapper.toInput(request);

		// Assert
		assertNotNull(result);
		assertFalse(result.guest());
		assertEquals("João Silva", result.name());
		assertEquals("joao.silva", result.username());
		assertEquals("joao@email.com", result.email());
		assertEquals("senha123", result.password());
		assertEquals("12345678901", result.document());
	}

	@Test @DisplayName("Deve mapear UserInput para User domain com sucesso")
	void shouldMapUserInputToDomain() {
		// Arrange
		UserInput input = new UserInput(true, "Maria Santos", "maria.santos", "maria@email.com", "minhasenha",
				"98765432100");

		// Act
		User result = UserMapper.toDomain(input);

		// Assert
		assertNotNull(result);
		assertTrue(result.isGuest());
		assertEquals("Maria Santos", result.getName());
		assertEquals("maria.santos", result.getUsername());
		assertEquals("maria@email.com", result.getEmail());
		assertEquals("minhasenha", result.getPassword());
		assertEquals("98765432100", result.getDocument());
	}

	@Test @DisplayName("Deve mapear UserRequest com valores nulos")
	void shouldMapUserRequestWithNullValues() {
		// Arrange
		UserRequest request = new UserRequest();
		request.setGuest(true);
		request.setName(null);
		request.setUsername(null);
		request.setEmail(null);
		request.setPassword(null);
		request.setDocument(null);

		// Act
		UserInput result = UserMapper.toInput(request);

		// Assert
		assertNotNull(result);
		assertTrue(result.guest());
		assertNull(result.name());
		assertNull(result.username());
		assertNull(result.email());
		assertNull(result.password());
		assertNull(result.document());
	}
}
