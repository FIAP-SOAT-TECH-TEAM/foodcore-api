package com.soat.fiap.food.core.api.user.unit.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.controller.web.api.GetAllUsersController;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.dto.mappers.UserDTOMapper;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.response.UserResponse;

@ExtendWith(MockitoExtension.class) @DisplayName("GetAllUsersController - Testes Unitários")
class GetAllUsersControllerTest {

	@Mock
	private UserDataSource userDataSource;

	@Test @DisplayName("Deve buscar todos os usuários com sucesso")
	void shouldFetchAllUsersSuccessfully() {
		// Arrange
		var users = List.of(UserFixture.createValidUser(), UserFixture.createUserWithEmail(),
				UserFixture.createUserWithDocument());
		var userDTOs = users.stream().map(UserDTOMapper::toDTO).toList();

		when(userDataSource.findAll()).thenReturn(userDTOs);

		// Act
		var result = GetAllUsersController.getAllUsers(userDataSource);

		var expectedResponses = userDTOs.stream()
				.map(dto -> new UserResponse(dto.id(), dto.name(), dto.username(), dto.email(), dto.document(),
						dto.active(), dto.roleId(), dto.createdAt(), dto.updatedAt(), null // token
				))
				.toList();

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).hasSize(3);
		assertThat(result).isEqualTo(expectedResponses);

		verify(userDataSource).findAll();
	}

	@Test
	@DisplayName("Deve retornar lista vazia quando não há usuários")
	void shouldReturnEmptyListWhenNoUsersExist() {
		// Arrange
		when(userDataSource.findAll()).thenReturn(List.of());

		// Act
		var result = GetAllUsersController.getAllUsers(userDataSource);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).isEmpty();

		verify(userDataSource).findAll();
	}

	@Test @DisplayName("Deve verificar se controller não lança exceção ao ser instanciado")
	void shouldVerifyControllerDoesNotThrowExceptionWhenInstantiated() {
		// Act & Assert
		assertDoesNotThrow(() -> new GetAllUsersController());
	}

	@Test @DisplayName("Deve processar lista com apenas um usuário")
	void shouldProcessListWithOnlyOneUser() {
		// Arrange
		var user = UserFixture.createValidUser();
		var userDTO = UserDTOMapper.toDTO(user);
		var userDTOs = List.of(userDTO);

		when(userDataSource.findAll()).thenReturn(userDTOs);

		// Act
		var result = GetAllUsersController.getAllUsers(userDataSource);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result).hasSize(1);
		assertThat(result.getFirst()).isEqualTo(userDTO);

		verify(userDataSource).findAll();
	}
}
