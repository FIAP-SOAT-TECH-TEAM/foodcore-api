package com.soat.fiap.food.core.api.user.unit.usecases;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import com.soat.fiap.food.core.api.user.core.application.usecases.GetUserByIdUseCase;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;

@ExtendWith(MockitoExtension.class) @DisplayName("GetUserByIdUseCase - Testes Unitários")
class GetUserByIdUseCaseTest {

	@Mock
	private UserGateway userGateway;

	@Test @DisplayName("Deve retornar usuário quando encontrado por ID")
	void shouldReturnUserWhenFoundById() {
		// Arrange
		Long userId = 1L;
		User expectedUser = UserFixture.createValidUser();
		expectedUser.setId(userId);

		when(userGateway.findById(userId)).thenReturn(Optional.of(expectedUser));

		// Act
		User result = GetUserByIdUseCase.getUserById(userId, userGateway);

		// Assert
		assertNotNull(result);
		assertEquals(expectedUser.getId(), result.getId());
		assertEquals(expectedUser.getName(), result.getName());
		assertEquals(expectedUser.getEmail(), result.getEmail());
		verify(userGateway).findById(userId);
	}

	@Test @DisplayName("Deve lançar exceção quando usuário não for encontrado")
	void shouldThrowExceptionWhenUserNotFound() {
		// Arrange
		Long userId = 999L;

		when(userGateway.findById(userId)).thenReturn(Optional.empty());

		// Act & Assert
		UserNotFoundException exception = assertThrows(UserNotFoundException.class, () -> {
			GetUserByIdUseCase.getUserById(userId, userGateway);
		});

		assertEquals("Usuário não encontrado com id: 999", exception.getMessage());
		verify(userGateway).findById(userId);
	}

	@Test @DisplayName("Deve chamar gateway com ID correto")
	void shouldCallGatewayWithCorrectId() {
		// Arrange
		Long userId = 42L;
		User user = UserFixture.createValidUser();
		user.setId(userId);

		when(userGateway.findById(userId)).thenReturn(Optional.of(user));

		// Act
		GetUserByIdUseCase.getUserById(userId, userGateway);

		// Assert
		verify(userGateway, times(1)).findById(userId);
		verifyNoMoreInteractions(userGateway);
	}

	@Test @DisplayName("Deve retornar usuário guest quando encontrado")
	void shouldReturnGuestUserWhenFound() {
		// Arrange
		Long userId = 10L;
		User guestUser = UserFixture.createGuestUser();
		guestUser.setId(userId);

		when(userGateway.findById(userId)).thenReturn(Optional.of(guestUser));

		// Act
		User result = GetUserByIdUseCase.getUserById(userId, userGateway);

		// Assert
		assertNotNull(result);
		assertTrue(result.isGuest());
		assertEquals(userId, result.getId());
		verify(userGateway).findById(userId);
	}

	@Test @DisplayName("Deve retornar usuário com documento quando encontrado")
	void shouldReturnUserWithDocumentWhenFound() {
		// Arrange
		Long userId = 5L;
		User userWithDocument = UserFixture.createUserWithDocument();
		userWithDocument.setId(userId);

		when(userGateway.findById(userId)).thenReturn(Optional.of(userWithDocument));

		// Act
		User result = GetUserByIdUseCase.getUserById(userId, userGateway);

		// Assert
		assertNotNull(result);
		assertTrue(result.hasDocument());
		assertTrue(result.isValidDocument());
		assertEquals(userId, result.getId());
		verify(userGateway).findById(userId);
	}
}
