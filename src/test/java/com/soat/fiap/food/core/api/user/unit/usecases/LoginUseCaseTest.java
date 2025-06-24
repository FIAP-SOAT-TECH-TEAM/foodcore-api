package com.soat.fiap.food.core.api.user.unit.usecases;

import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.SecurityGateway;
import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import com.soat.fiap.food.core.api.user.core.application.usecases.LoginUseCase;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserIncorrectPasswordException;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("LoginUseCase - Testes Unitários")
class LoginUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Mock
    private SecurityGateway securityGateway;

    @Test
    @DisplayName("Deve fazer login com sucesso quando credenciais válidas")
    void shouldLoginSuccessfullyWhenValidCredentials() {
        // Arrange
        var user = UserFixture.createValidUser();
        user.setId(1L);
        var email = "test@example.com";
        var password = "password123";
        
        when(userGateway.findByEmail(email)).thenReturn(Optional.of(user));
        when(securityGateway.matches(password, user.getPassword())).thenReturn(true);

        // Act
        var result = LoginUseCase.login(email, password, userGateway, securityGateway);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.getEmail()).isEqualTo(user.getEmail());
        verify(userGateway).findByEmail(email);
        verify(securityGateway).matches(password, user.getPassword());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não encontrado")
    void shouldThrowExceptionWhenUserNotFound() {
        // Arrange
        var email = "notfound@example.com";
        var password = "password123";
        
        when(userGateway.findByEmail(email)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> LoginUseCase.login(email, password, userGateway, securityGateway))
                .isInstanceOf(UserNotFoundException.class)
                .hasMessage("Usuário não encontrado!");

        verify(userGateway).findByEmail(email);
    }

    @Test
    @DisplayName("Deve lançar exceção quando senha incorreta")
    void shouldThrowExceptionWhenIncorrectPassword() {
        // Arrange
        var user = UserFixture.createValidUser();
        user.setId(1L);
        var email = "test@example.com";
        var wrongPassword = "wrongpassword";
        
        when(userGateway.findByEmail(email)).thenReturn(Optional.of(user));
        when(securityGateway.matches(wrongPassword, user.getPassword())).thenReturn(false);

        // Act & Assert
        assertThatThrownBy(() -> LoginUseCase.login(email, wrongPassword, userGateway, securityGateway))
                .isInstanceOf(UserIncorrectPasswordException.class)
                .hasMessage("Email ou senha inválidos");

        verify(userGateway).findByEmail(email);
        verify(securityGateway).matches(wrongPassword, user.getPassword());
    }

    @Test
    @DisplayName("Deve retornar usuário completo após login bem-sucedido")
    void shouldReturnCompleteUserAfterSuccessfulLogin() {
        // Arrange
        var email = "maria@example.com";
        var rawPassword = "senha123";
        var hashedPassword = "hashed_senha123";
        
        var user = UserFixture.createValidUser();
        user.setId(42L);
        user.setName("Maria Silva");
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setDocument("12345678901");
        
        when(userGateway.findByEmail(email)).thenReturn(Optional.of(user));
        when(securityGateway.matches(rawPassword, hashedPassword)).thenReturn(true);

        // Act
        var result = LoginUseCase.login(email, rawPassword, userGateway, securityGateway);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(42L);
        assertThat(result.getName()).isEqualTo("Maria Silva");
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.getDocument()).isEqualTo("12345678901");
        assertThat(result.getPassword()).isEqualTo(hashedPassword);
        
        verify(userGateway).findByEmail(email);
        verify(securityGateway).matches(rawPassword, hashedPassword);
    }

    @Test
    @DisplayName("Deve validar diferentes emails e senhas")
    void shouldValidateDifferentEmailsAndPasswords() {
        // Arrange
        var email = "admin@company.com";
        var rawPassword = "admin_password";
        var hashedPassword = "hashed_admin_password";
        
        var user = UserFixture.createValidUser();
        user.setEmail(email);
        user.setPassword(hashedPassword);
        user.setActive(true);
        
        when(userGateway.findByEmail(email)).thenReturn(Optional.of(user));
        when(securityGateway.matches(rawPassword, hashedPassword)).thenReturn(true);

        // Act
        var result = LoginUseCase.login(email, rawPassword, userGateway, securityGateway);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result.getEmail()).isEqualTo(email);
        assertThat(result.isActive()).isTrue();
        
        verify(userGateway).findByEmail(email);
        verify(securityGateway).matches(rawPassword, hashedPassword);
    }
} 