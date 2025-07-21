package com.soat.fiap.food.core.api.order.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.order.core.application.usecases.AssignGuestUserToOrderUseCase;
import com.soat.fiap.food.core.api.shared.fixtures.OrderFixture;
import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;

@ExtendWith(MockitoExtension.class) @DisplayName("AssignGuestUserToOrderUseCase - Testes Unitários")
class AssignGuestUserToOrderUseCaseTest {

	@Mock
	private UserGateway userGateway;

	@Test @DisplayName("Deve atribuir usuário guest quando pedido não tem usuário")
	void shouldAssignGuestUserWhenOrderHasNoUser() {
		// Arrange
		var order = OrderFixture.createValidOrder();
		order.setUserId(null); // Pedido sem usuário

		var guestUser = UserFixture.createGuestUser();
		guestUser.setId(99L);

		when(userGateway.findFirstByGuestTrue()).thenReturn(Optional.of(guestUser));

		// Act
		AssignGuestUserToOrderUseCase.assignGuestUserToOrder(order, userGateway);

		// Assert
		assertThat(order.getUserId()).isEqualTo(99L);

		verify(userGateway).findFirstByGuestTrue();
	}

	@Test @DisplayName("Não deve fazer nada quando pedido já tem usuário")
	void shouldDoNothingWhenOrderAlreadyHasUser() {
		// Arrange
		var order = OrderFixture.createValidOrder();
		order.setUserId(1L); // Pedido já tem usuário
		var originalUserId = order.getUserId();

		// Act
		AssignGuestUserToOrderUseCase.assignGuestUserToOrder(order, userGateway);

		// Assert
		assertThat(order.getUserId()).isEqualTo(originalUserId);

		verifyNoInteractions(userGateway);
	}

	@Test @DisplayName("Deve lançar exceção quando usuário guest não for encontrado")
	void shouldThrowExceptionWhenGuestUserNotFound() {
		// Arrange
		var order = OrderFixture.createValidOrder();
		order.setUserId(null); // Pedido sem usuário

		when(userGateway.findFirstByGuestTrue()).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> AssignGuestUserToOrderUseCase.assignGuestUserToOrder(order, userGateway))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("Usuário GUEST não encontrado");

		verify(userGateway).findFirstByGuestTrue();
	}

	@Test @DisplayName("Deve atribuir usuário guest com ID específico")
	void shouldAssignGuestUserWithSpecificId() {
		// Arrange
		var order = OrderFixture.createValidOrder();
		order.setUserId(null); // Pedido sem usuário

		var guestUser = UserFixture.createGuestUser();
		guestUser.setId(777L);

		when(userGateway.findFirstByGuestTrue()).thenReturn(Optional.of(guestUser));

		// Act
		AssignGuestUserToOrderUseCase.assignGuestUserToOrder(order, userGateway);

		// Assert
		assertThat(order.getUserId()).isEqualTo(777L);
		assertThat(order.getUserId()).isNotNull();

		verify(userGateway).findFirstByGuestTrue();
	}

	@Test @DisplayName("Deve manter outras propriedades do pedido inalteradas")
	void shouldKeepOtherOrderPropertiesUnchanged() {
		// Arrange
		var order = OrderFixture.createValidOrder();
		order.setUserId(null); // Pedido sem usuário
		var originalOrderNumber = order.getOrderNumber();
		var originalAmount = order.getAmount();
		var originalStatus = order.getOrderStatus();

		var guestUser = UserFixture.createGuestUser();
		guestUser.setId(123L);

		when(userGateway.findFirstByGuestTrue()).thenReturn(Optional.of(guestUser));

		// Act
		AssignGuestUserToOrderUseCase.assignGuestUserToOrder(order, userGateway);

		// Assert
		assertThat(order.getUserId()).isEqualTo(123L);
		assertThat(order.getOrderNumber()).isEqualTo(originalOrderNumber);
		assertThat(order.getAmount()).isEqualTo(originalAmount);
		assertThat(order.getOrderStatus()).isEqualTo(originalStatus);

		verify(userGateway).findFirstByGuestTrue();
	}
}
