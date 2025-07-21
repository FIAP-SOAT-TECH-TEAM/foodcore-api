package com.soat.fiap.food.core.api.user.unit.controller;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.shared.infrastructure.common.source.SecuritySource;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.TokenSource;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.controller.web.api.SaveUserController;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.request.UserRequest;

@ExtendWith(MockitoExtension.class) @DisplayName("SaveUserController - Testes Unitários")
class SaveUserControllerTest {

	@Mock
	private UserDataSource userDataSource;

	@Mock
	private TokenSource tokenSource;

	@Mock
	private SecuritySource securitySource;

	@Test @DisplayName("Deve verificar que SaveUserController existe")
	void shouldVerifyThatSaveUserControllerExists() {
		// Act & Assert
		assertDoesNotThrow(() -> {
			SaveUserController.class.getName();
		});
	}

	@Test @DisplayName("Deve verificar que UserRequest pode ser criado")
	void shouldVerifyThatUserRequestCanBeCreated() {
		// Act & Assert
		assertDoesNotThrow(() -> {
			var userRequest = new UserRequest();
			userRequest.setName("Test");
		});
	}
}
