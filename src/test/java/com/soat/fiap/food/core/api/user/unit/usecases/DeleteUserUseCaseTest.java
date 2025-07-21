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
import com.soat.fiap.food.core.api.user.core.application.usecases.DeleteUserUseCase;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;

@ExtendWith(MockitoExtension.class) @DisplayName("DeleteUserUseCase - Testes Unitários")
class DeleteUserUseCaseTest {

	@Mock
	private UserGateway userGateway;

	@Test @DisplayName("Deve excluir usuário com sucesso quando usuário existe")
	void shouldDeleteUserSuccessfullyWhenUserExists() {
		// Arrange
		var user = UserFixture.createValidUser();
		user.setId(1L);

		when(userGateway.findById(1L)).thenReturn(Optional.of(user));

		// Act
		assertDoesNotThrow(() -> DeleteUserUseCase.deleteUser(1L, userGateway));

		// Assert
		verify(userGateway, times(1)).findById(1L);
		verify(userGateway, times(1)).delete(1L);
	}

	@Test
	@DisplayName("Deve lançar exceção quando usuário não existe")
	void shouldThrowExceptionWhenUserNotExists() {
		// Arrange
		when(userGateway.findById(999L)).thenReturn(Optional.empty());

		// Act & Assert
		var exception = assertThrows(UserNotFoundException.class,
				() -> DeleteUserUseCase.deleteUser(999L, userGateway));

		assertEquals("Usuário não encontrado com id: 999", exception.getMessage());
		verify(userGateway, times(1)).findById(999L);
		verify(userGateway, never()).delete(999L);
	}

	@Test @DisplayName("Deve verificar se usuário existe antes de tentar excluir")
	void shouldVerifyUserExistsBeforeDeleting() {
		// Arrange
		var user = UserFixture.createUserWithDocument();
		user.setId(42L);

		when(userGateway.findById(42L)).thenReturn(Optional.of(user));

		// Act
		DeleteUserUseCase.deleteUser(42L, userGateway);

		// Assert
		verify(userGateway, times(1)).findById(42L);
		verify(userGateway, times(1)).delete(42L);
	}

	@Test @DisplayName("Deve excluir usuário com ID específico")
	void shouldDeleteUserWithSpecificId() {
		// Arrange
		var userId = 123L;
		var user = UserFixture.createValidUser();
		user.setId(userId);

		when(userGateway.findById(userId)).thenReturn(Optional.of(user));

		// Act
		DeleteUserUseCase.deleteUser(userId, userGateway);

		// Assert
		verify(userGateway).delete(userId);
	}
}
