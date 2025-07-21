package com.soat.fiap.food.core.api.user.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.controller.web.api.GetUserByIdController;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.dto.mappers.UserDTOMapper;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;

@ExtendWith(MockitoExtension.class) @DisplayName("GetUserByIdController - Testes Unitários")
class GetUserByIdControllerTest {

	@Mock
	private UserDataSource userDataSource;

	@Test @DisplayName("Deve buscar usuário por ID com sucesso")
	void shouldFindUserByIdSuccessfully() {
		// Arrange
		var userId = 1L;
		var user = UserFixture.createValidUser();

		when(userDataSource.findById(userId)).thenReturn(Optional.of(user).map(UserDTOMapper::toDTO));

		// Act
		var result = GetUserByIdController.getUserById(userId, userDataSource);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(user.getName());
		assertThat(result.getEmail()).isEqualTo(user.getEmail());

		verify(userDataSource).findById(userId);
	}

	@Test @DisplayName("Deve verificar se controller não lança exceção ao ser instanciado")
	void shouldVerifyControllerDoesNotThrowExceptionWhenInstantiated() {
		// Act & Assert
		assertDoesNotThrow(() -> new GetUserByIdController());
	}

	@Test @DisplayName("Deve processar busca de usuário com email diferente")
	void shouldProcessUserSearchWithDifferentEmail() {
		// Arrange
		var userId = 2L;
		var user = UserFixture.createUserWithEmail();

		when(userDataSource.findById(userId)).thenReturn(Optional.of(user).map(UserDTOMapper::toDTO));

		// Act
		var result = GetUserByIdController.getUserById(userId, userDataSource);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getName()).isEqualTo(user.getName());
		assertThat(result.getEmail()).isEqualTo(user.getEmail());

		verify(userDataSource).findById(userId);
	}
}
