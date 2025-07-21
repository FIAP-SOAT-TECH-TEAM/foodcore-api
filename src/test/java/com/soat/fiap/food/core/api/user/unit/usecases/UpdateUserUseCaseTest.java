package com.soat.fiap.food.core.api.user.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import com.soat.fiap.food.core.api.user.core.application.inputs.UserInput;
import com.soat.fiap.food.core.api.user.core.application.usecases.UpdateUserUseCase;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;

@ExtendWith(MockitoExtension.class) @DisplayName("UpdateUserUseCase - Testes Unitários")
class UpdateUserUseCaseTest {

	@Mock
	private UserGateway userGateway;

	@Mock
	private SecurityGateway securityGateway;

	@Test @DisplayName("Deve atualizar usuário com sucesso")
	void shouldUpdateUserSuccessfully() {
		// Arrange
		var existingUser = UserFixture.createValidUser();
		existingUser.setId(1L);
		existingUser.setDocument("12345678901"); // Mesmo documento do input

		var userInput = new UserInput(false, "João Silva Atualizado", "joao.atualizado", "joao.novo@email.com",
				"novaSenha123456", "12345678901");

		when(userGateway.findById(1L)).thenReturn(Optional.of(existingUser));
		when(securityGateway.encodePassword("novaSenha123456")).thenReturn("senhaEncriptada");

		// Act
		var result = UpdateUserUseCase.updateUser(1L, userInput, userGateway, securityGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(1L);
		assertThat(result.getName()).isEqualTo("João Silva Atualizado");
		assertThat(result.getUsername()).isEqualTo("joao.atualizado");
		assertThat(result.getEmail()).isEqualTo("joao.novo@email.com");
		assertThat(result.getPassword()).isEqualTo("senhaEncriptada");

		verify(userGateway).findById(1L);
		verify(securityGateway).encodePassword("novaSenha123456");
	}

	@Test @DisplayName("Deve lançar exceção quando usuário não for encontrado")
	void shouldThrowExceptionWhenUserNotFound() {
		// Arrange
		var userInput = new UserInput(false, "Nome Qualquer", "username", "email@test.com", "senha123456",
				"12345678901");

		when(userGateway.findById(999L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> UpdateUserUseCase.updateUser(999L, userInput, userGateway, securityGateway))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("Usuário não encontrado com id: 999");

		verify(userGateway).findById(999L);
	}

	@Test @DisplayName("Deve atualizar usuário com documento diferente")
	void shouldUpdateUserWithDifferentDocument() {
		// Arrange
		var existingUser = UserFixture.createValidUser();
		existingUser.setId(2L);
		existingUser.setDocument("11111111111");

		var userInput = new UserInput(false, "Nome Atualizado", "username.novo", "novo@email.com", "novaSenha123456",
				"22222222222");

		when(userGateway.findById(2L)).thenReturn(Optional.of(existingUser));
		when(userGateway.findByDocument("22222222222")).thenReturn(Optional.empty());
		when(securityGateway.encodePassword("novaSenha123456")).thenReturn("senhaEncriptada");

		// Act
		var result = UpdateUserUseCase.updateUser(2L, userInput, userGateway, securityGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(2L);
		assertThat(result.getDocument()).isEqualTo("22222222222");

		verify(userGateway).findById(2L);
		verify(userGateway).findByDocument("22222222222");
		verify(securityGateway).encodePassword("novaSenha123456");
	}

	@Test @DisplayName("Deve manter ID do usuário inalterado durante atualização")
	void shouldKeepUserIdUnchangedDuringUpdate() {
		// Arrange
		var existingUser = UserFixture.createValidUser();
		existingUser.setId(5L);

		var userInput = new UserInput(false, "Novo Nome", "novo.username", "novo@email.com", "novaSenha123456",
				"98765432100");

		when(userGateway.findById(5L)).thenReturn(Optional.of(existingUser));
		when(userGateway.findByDocument("98765432100")).thenReturn(Optional.empty());
		when(securityGateway.encodePassword(anyString())).thenReturn("senhaEncriptada");

		// Act
		var result = UpdateUserUseCase.updateUser(5L, userInput, userGateway, securityGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(5L);
		assertThat(result.getName()).isEqualTo("Novo Nome");

		verify(userGateway).findById(5L);
		verify(securityGateway).encodePassword(anyString());
	}

	@Test @DisplayName("Deve validar dados do usuário durante atualização")
	void shouldValidateUserDataDuringUpdate() {
		// Arrange
		var existingUser = UserFixture.createValidUser();
		existingUser.setId(3L);
		existingUser.setDocument("11122233344");

		var userInput = new UserInput(false, "Maria Santos", "maria.santos", "maria@email.com", "senha123456",
				"11122233344");

		when(userGateway.findById(3L)).thenReturn(Optional.of(existingUser));
		when(securityGateway.encodePassword("senha123456")).thenReturn("senhaEncriptada");

		// Act
		var result = UpdateUserUseCase.updateUser(3L, userInput, userGateway, securityGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getId()).isEqualTo(3L);
		assertThat(result.getName()).isEqualTo("Maria Santos");
		assertThat(result.getUsername()).isEqualTo("maria.santos");
		assertThat(result.getEmail()).isEqualTo("maria@email.com");

		verify(userGateway).findById(3L);
		verify(securityGateway).encodePassword("senha123456");
	}
}
