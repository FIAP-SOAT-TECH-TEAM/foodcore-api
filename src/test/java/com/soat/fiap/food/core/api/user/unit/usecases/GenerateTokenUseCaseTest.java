package com.soat.fiap.food.core.api.user.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.TokenGateway;
import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import com.soat.fiap.food.core.api.user.core.application.usecases.GenerateTokenUseCase;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserException;

@ExtendWith(MockitoExtension.class) @DisplayName("GenerateTokenUseCase - Testes Unitários")
class GenerateTokenUseCaseTest {

	@Mock
	private TokenGateway tokenGateway;

	@Test @DisplayName("Deve gerar token para usuário válido com ID")
	void shouldGenerateTokenForValidUserWithId() {
		// Arrange
		var user = UserFixture.createValidUser();
		user.setId(1L); // Definindo ID para o usuário
		var expectedToken = "token-generated-123";

		when(tokenGateway.generateToken(user)).thenReturn(expectedToken);

		// Act
		var result = GenerateTokenUseCase.generateToken(user, tokenGateway);

		// Assert
		assertThat(result).isNotNull();
		assertThat(result.getToken()).isEqualTo(expectedToken);
		assertThat(result).isSameAs(user); // Deve retornar o mesmo usuário

		verify(tokenGateway).generateToken(user);
	}

	@Test @DisplayName("Deve lançar exceção para usuário nulo")
	void shouldThrowExceptionForNullUser() {
		// Act & Assert
		assertThatThrownBy(() -> GenerateTokenUseCase.generateToken(null, tokenGateway))
				.isInstanceOf(UserException.class)
				.hasMessage("Um usuário não nulo e com ID deve ser informado para geração do token");

		verify(tokenGateway, never()).generateToken(any());
	}

	@Test @DisplayName("Deve lançar exceção para usuário sem ID")
	void shouldThrowExceptionForUserWithoutId() {
		// Arrange
		var user = UserFixture.createValidUser();
		// user.getId() será nulo por padrão

		// Act & Assert
		assertThatThrownBy(() -> GenerateTokenUseCase.generateToken(user, tokenGateway))
				.isInstanceOf(UserException.class)
				.hasMessage("Um usuário não nulo e com ID deve ser informado para geração do token");

		verify(tokenGateway, never()).generateToken(any());
	}

	@Test @DisplayName("Deve chamar o gateway uma única vez")
	void shouldCallGatewayOnlyOnce() {
		// Arrange
		var user = UserFixture.createValidUser();
		user.setId(2L);
		var expectedToken = "unique-token-789";

		when(tokenGateway.generateToken(user)).thenReturn(expectedToken);

		// Act
		GenerateTokenUseCase.generateToken(user, tokenGateway);

		// Assert
		verify(tokenGateway, times(1)).generateToken(user);
	}
}
