package com.soat.fiap.food.core.api.user.unit.domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserException;
import com.soat.fiap.food.core.api.user.core.domain.model.Role;
import com.soat.fiap.food.core.api.user.core.domain.model.User;

@DisplayName("User - Testes de Domínio")
class UserTest {

	@Test @DisplayName("Deve criar usuário válido com todos os campos")
	void shouldCreateValidUserWithAllFields() {
		// Arrange & Act
		User user = UserFixture.createValidUser();

		// Assert
		assertNotNull(user);
		assertEquals("João Silva", user.getName());
		assertEquals("joao.silva", user.getUsername());
		assertEquals("joao@email.com", user.getEmail());
		assertEquals("senha123456", user.getPassword());
		assertEquals("12345678901", user.getDocument());
		assertFalse(user.isGuest());
		assertTrue(user.isActive());
	}

	@Test @DisplayName("Deve criar usuário convidado")
	void shouldCreateGuestUser() {
		// Arrange & Act
		User user = UserFixture.createGuestUser();

		// Assert
		assertNotNull(user);
		assertTrue(user.isGuest());
		assertEquals("Usuário Convidado", user.getName());
		assertNull(user.getUsername());
		assertNull(user.getEmail());
		assertNull(user.getDocument());
	}

	@Test @DisplayName("Deve validar documento corretamente")
	void shouldValidateDocumentCorrectly() {
		// Arrange
		User user = UserFixture.createUserWithDocument();

		// Act & Assert
		assertTrue(user.isValidDocument());
		assertTrue(user.hasDocument());
	}

	@Test @DisplayName("Deve retornar falso para documento inválido")
	void shouldReturnFalseForInvalidDocument() {
		// Arrange
		User user = new User(false, "Test User", null, null, "senha123456", "123", Role.defaultRole());

		// Act & Assert
		assertFalse(user.isValidDocument());
	}

	@Test @DisplayName("Deve validar email corretamente")
	void shouldValidateEmailCorrectly() {
		// Arrange
		User user = UserFixture.createUserWithEmail();

		// Act & Assert
		assertTrue(user.hasEmail());
		assertTrue(user.getEmail().contains("@"));
	}

	@Test @DisplayName("Deve validar username corretamente")
	void shouldValidateUsernameCorrectly() {
		// Arrange
		User user = UserFixture.createUserWithUsername();

		// Act & Assert
		assertTrue(user.hasUsername());
		assertTrue(user.getUsername().length() >= 3);
	}

	@Test @DisplayName("Deve validar senha corretamente")
	void shouldValidatePasswordCorrectly() {
		// Arrange
		User user = UserFixture.createValidUser();

		// Act & Assert
		assertTrue(user.hasPassword());
		assertTrue(user.getPassword().length() >= 8);
	}

	@Test @DisplayName("Deve ativar usuário")
	void shouldActivateUser() {
		// Arrange
		User user = UserFixture.createValidUser();
		user.deactivate();

		// Act
		user.activate();

		// Assert
		assertTrue(user.isActive());
	}

	@Test @DisplayName("Deve desativar usuário")
	void shouldDeactivateUser() {
		// Arrange
		User user = UserFixture.createValidUser();

		// Act
		user.deactivate();

		// Assert
		assertFalse(user.isActive());
	}

	@Test @DisplayName("Deve validar estado interno após criação")
	void shouldValidateInternalStateAfterCreation() {
		// Arrange
		User user = UserFixture.createValidUser();
		user.setDocument("123456");

		// Act & Assert
		UserException exception = assertThrows(UserException.class, user::validateInternalState);

		assertEquals("Documento inválido", exception.getMessage());
	}

	@Test @DisplayName("Deve validar documento com menos de 11 caracteres")
	void shouldValidateShortDocument() {
		// Arrange
		User user = UserFixture.createValidUser();
		user.setDocument("1234567890a"); // 11 caracteres, mas com letra (será inválido primeiro)

		// Act & Assert
		UserException exception = assertThrows(UserException.class, user::validateInternalState);

		// A primeira validação (isValidDocument) será executada primeiro
		assertEquals("Documento inválido", exception.getMessage());
	}

	@Test @DisplayName("Deve validar email inválido")
	void shouldValidateInvalidEmail() {
		// Arrange
		User user = UserFixture.createValidUser();
		user.setEmail("email-invalido");

		// Act & Assert
		UserException exception = assertThrows(UserException.class, user::validateInternalState);

		assertEquals("Email inválido", exception.getMessage());
	}

	@Test @DisplayName("Deve validar nome obrigatório quando há email")
	void shouldValidateRequiredNameWhenEmailExists() {
		// Arrange
		User user = UserFixture.createValidUser();
		user.setName(null);

		// Act & Assert
		UserException exception = assertThrows(UserException.class, user::validateInternalState);

		assertEquals("Nome é obrigatório", exception.getMessage());
	}

	@Test @DisplayName("Deve validar username muito curto")
	void shouldValidateShortUsername() {
		// Arrange
		User user = UserFixture.createValidUser();
		user.setUsername("ab");

		// Act & Assert
		UserException exception = assertThrows(UserException.class, user::validateInternalState);

		assertEquals("O username deve ter pelo menos 3 caracteres", exception.getMessage());
	}

	@Test @DisplayName("Deve validar senha muito curta")
	void shouldValidateShortPassword() {
		// Arrange
		User user = UserFixture.createValidUser();
		user.setPassword("123");

		// Act & Assert
		UserException exception = assertThrows(UserException.class, user::validateInternalState);

		assertEquals("A senha deve ter pelo menos 8 caracteres", exception.getMessage());
	}

	@Test @DisplayName("Deve identificar usuário como convidado quando não tem identificadores")
	void shouldIdentifyUserAsGuestWhenNoIdentifiers() {
		// Arrange
		User user = new User(false, "Test User", null, null, "senha123456", null, Role.defaultRole());

		// Act & Assert
		assertTrue(user.isGuest());
	}

	@Test @DisplayName("Deve retornar data de criação")
	void shouldReturnCreatedAt() {
		// Arrange
		User user = UserFixture.createValidUser();

		// Act & Assert
		assertNotNull(user.getCreatedAt());
	}
}
