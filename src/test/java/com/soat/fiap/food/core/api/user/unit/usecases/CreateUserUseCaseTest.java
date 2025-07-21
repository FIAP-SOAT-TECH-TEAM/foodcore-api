package com.soat.fiap.food.core.api.user.unit.usecases;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.SecurityGateway;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.TokenGateway;
import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import com.soat.fiap.food.core.api.user.core.application.inputs.UserInput;
import com.soat.fiap.food.core.api.user.core.application.usecases.CreateUserUseCase;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;

@ExtendWith(MockitoExtension.class) @DisplayName("CreateUserUseCase - Testes Unitários")
class CreateUserUseCaseTest {

	@Mock
	private UserGateway userGateway;

	@Mock
	private SecurityGateway securityGateway;

	@Mock
	private TokenGateway tokenGateway;

	@Test @DisplayName("Deve retornar usuário existente quando buscar por documento")
	void shouldReturnExistingUserWhenFoundByDocument() {
		// Arrange
		var userInput = new UserInput(false, // guest
				"João Silva", "joaosilva", "joao@example.com", "password123", "12345678901");

		var existingUser = UserFixture.createValidUser();

		when(userGateway.findByDocument(userInput.document())).thenReturn(Optional.of(existingUser));

		// Act
		var result = CreateUserUseCase.createUser(userInput, userGateway, tokenGateway, securityGateway);

		// Assert
		assertNotNull(result);
		assertEquals(existingUser.getId(), result.getId());
		assertEquals(existingUser.getName(), result.getName());

		verify(userGateway).findByDocument(userInput.document());
	}

	@Test @DisplayName("Deve retornar usuário existente quando buscar por email")
	void shouldReturnExistingUserWhenFoundByEmail() {
		// Arrange
		var userInput = new UserInput(false, // guest
				"João Silva", "joaosilva", "joao@example.com", "password123", null // sem documento
		);

		var existingUser = UserFixture.createValidUser();

		when(userGateway.findByEmail(userInput.email())).thenReturn(Optional.of(existingUser));

		// Act
		var result = CreateUserUseCase.createUser(userInput, userGateway, tokenGateway, securityGateway);

		// Assert
		assertNotNull(result);
		assertEquals(existingUser.getId(), result.getId());
		assertEquals(existingUser.getEmail(), result.getEmail());

		verify(userGateway).findByEmail(userInput.email());
	}

	@Test @DisplayName("Deve criptografar senha quando usuário tem senha e não tem ID")
	void shouldEncodePasswordWhenUserHasPasswordAndNoId() {
		// Arrange
		var userInput = new UserInput(false, // guest
				"João Silva", "joaosilva", "joao@example.com", "password123", null // sem documento
		);

		var hashedPassword = "hashed_password";

		when(userGateway.findByEmail(userInput.email())).thenReturn(Optional.empty());
		when(securityGateway.encodePassword(anyString())).thenReturn(hashedPassword);

		// Act
		var result = CreateUserUseCase.createUser(userInput, userGateway, tokenGateway, securityGateway);

		// Assert
		assertNotNull(result);
		assertEquals(hashedPassword, result.getPassword());

		verify(securityGateway).encodePassword(userInput.password());
	}
}
