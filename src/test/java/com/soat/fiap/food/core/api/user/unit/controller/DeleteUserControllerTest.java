package com.soat.fiap.food.core.api.user.unit.controller;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.controller.web.api.DeleteUserController;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.dto.mappers.UserDTOMapper;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;

@ExtendWith(MockitoExtension.class) @DisplayName("DeleteUserController - Testes Unitários")
class DeleteUserControllerTest {

	@Mock
	private UserDataSource userDataSource;

	@Test @DisplayName("Deve deletar usuário com sucesso")
	void shouldDeleteUserSuccessfully() {
		// Arrange
		var userId = 1L;
		var user = UserFixture.createValidUser();

		when(userDataSource.findById(userId)).thenReturn(Optional.of(user).map(UserDTOMapper::toDTO));

		// Act & Assert
		assertThatNoException().isThrownBy(() -> DeleteUserController.deleteUser(userId, userDataSource));

		verify(userDataSource).findById(userId);
		verify(userDataSource).delete(userId);
	}

	@Test @DisplayName("Deve lançar exceção quando usuário não é encontrado para deletar")
	void shouldThrowExceptionWhenUserNotFoundForDeletion() {
		// Arrange
		var userId = 999L;

		when(userDataSource.findById(userId)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> DeleteUserController.deleteUser(userId, userDataSource))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessageContaining("Usuário");

		verify(userDataSource).findById(userId);
		// Delete não deve ser chamado quando usuário não existe
	}

	@Test @DisplayName("Deve chamar métodos corretos do dataSource")
	void shouldCallCorrectDataSourceMethods() {
		// Arrange
		var userId = 123L;
		var user = UserFixture.createValidUser();

		when(userDataSource.findById(userId)).thenReturn(Optional.of(user).map(UserDTOMapper::toDTO));

		// Act
		DeleteUserController.deleteUser(userId, userDataSource);

		// Assert
		verify(userDataSource).findById(userId);
		verify(userDataSource).delete(userId);
	}
}
