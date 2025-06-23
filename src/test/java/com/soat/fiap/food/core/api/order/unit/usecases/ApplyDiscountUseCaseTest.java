package com.soat.fiap.food.core.api.order.unit.usecases;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.api.order.core.application.usecases.ApplyDiscountUseCase;
import com.soat.fiap.food.core.api.shared.fixtures.OrderFixture;
import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;

@ExtendWith(MockitoExtension.class) @DisplayName("ApplyDiscountUseCase - Testes Unitários")
class ApplyDiscountUseCaseTest {

	@Mock
	private UserGateway userGateway;

	@Test @DisplayName("Deve aplicar desconto de 10% para usuário com 5 anos de cadastro")
	void shouldApply10PercentDiscountForUserWith5YearsOfRegistration() {
		// Arrange
		var order = OrderFixture.createValidOrder();
		var originalAmount = order.getAmount(); // 51.80

		var user = UserFixture.createUserWithCreationDate(LocalDateTime.now().minusYears(5));
		user.setId(1L);

		when(userGateway.findById(1L)).thenReturn(Optional.of(user));

		// Act
		ApplyDiscountUseCase.applyDiscount(order, userGateway);

		// Assert
		assertThat(order.getAmount()).isLessThan(originalAmount);
		// Com 10% de desconto: 51.80 - (51.80 * 0.10) = 46.62
		assertThat(order.getAmount()).isEqualByComparingTo(new BigDecimal("46.6200"));

		verify(userGateway).findById(1L);
	}

	@Test @DisplayName("Deve aplicar desconto de 20% para usuário com 10 anos de cadastro")
	void shouldApply20PercentDiscountForUserWith10YearsOfRegistration() {
		// Arrange
		var order = OrderFixture.createValidOrder();
		var originalAmount = order.getAmount(); // 51.80

		var user = UserFixture.createUserWithCreationDate(LocalDateTime.now().minusYears(10));
		user.setId(1L);

		when(userGateway.findById(1L)).thenReturn(Optional.of(user));

		// Act
		ApplyDiscountUseCase.applyDiscount(order, userGateway);

		// Assert
		assertThat(order.getAmount()).isLessThan(originalAmount);
		// Com 20% de desconto: 51.80 - (51.80 * 0.20) = 41.44
		assertThat(order.getAmount()).isEqualByComparingTo(new BigDecimal("41.4400"));

		verify(userGateway).findById(1L);
	}

	@Test @DisplayName("Não deve aplicar desconto para usuário com menos de 1 ano de cadastro")
	void shouldNotApplyDiscountForUserWithLessThan1YearOfRegistration() {
		// Arrange
		var order = OrderFixture.createValidOrder();
		var originalAmount = order.getAmount(); // 51.80

		// Usuário criado no mesmo ano = diferença de anos = 0
		var currentYear = LocalDateTime.now().getYear();
		var sameYearDate = LocalDateTime.of(currentYear, 1, 1, 10, 0);
		var user = UserFixture.createUserWithCreationDate(sameYearDate);
		user.setId(1L);

		when(userGateway.findById(1L)).thenReturn(Optional.of(user));

		// Act
		ApplyDiscountUseCase.applyDiscount(order, userGateway);

		// Assert
		assertThat(order.getAmount()).isEqualByComparingTo(originalAmount);

		verify(userGateway).findById(1L);
	}

	@Test @DisplayName("Deve aplicar desconto máximo de 94% para usuário muito antigo")
	void shouldApplyMaximumDiscountForVeryOldUser() {
		// Arrange
		var order = OrderFixture.createValidOrder();
		var originalAmount = order.getAmount(); // 51.80

		var user = UserFixture.createUserWithCreationDate(LocalDateTime.now().minusYears(47));
		user.setId(1L);

		when(userGateway.findById(1L)).thenReturn(Optional.of(user));

		// Act
		ApplyDiscountUseCase.applyDiscount(order, userGateway);

		// Assert
		assertThat(order.getAmount()).isLessThan(originalAmount);
		// Com 94% de desconto: 51.80 - (51.80 * 0.94) = 3.108
		assertThat(order.getAmount()).isEqualByComparingTo(new BigDecimal("3.1080"));

		verify(userGateway).findById(1L);
	}

	@Test @DisplayName("Não deve aplicar desconto superior a 95% mesmo para usuário muito antigo")
	void shouldNotApplyDiscountOver95PercentEvenForVeryOldUser() {
		// Arrange
		var order = OrderFixture.createValidOrder();
		var originalAmount = order.getAmount(); // 51.80

		var user = UserFixture.createUserWithCreationDate(LocalDateTime.now().minusYears(50));
		user.setId(1L);

		when(userGateway.findById(1L)).thenReturn(Optional.of(user));

		// Act
		ApplyDiscountUseCase.applyDiscount(order, userGateway);

		// Assert
		assertThat(order.getAmount()).isEqualByComparingTo(originalAmount); // Não deve aplicar desconto

		verify(userGateway).findById(1L);
	}

	@Test @DisplayName("Deve lançar exceção quando usuário não for encontrado")
	void shouldThrowExceptionWhenUserNotFound() {
		// Arrange
		var order = OrderFixture.createValidOrder();

		when(userGateway.findById(1L)).thenReturn(Optional.empty());

		// Act & Assert
		assertThatThrownBy(() -> ApplyDiscountUseCase.applyDiscount(order, userGateway))
				.isInstanceOf(UserNotFoundException.class)
				.hasMessage("Cliente do pedido não encontrado");

		verify(userGateway).findById(1L);
	}

	@Test @DisplayName("Deve aplicar desconto de 2% para usuário com 1 ano de cadastro")
	void shouldApply2PercentDiscountForUserWith1YearOfRegistration() {
		// Arrange
		var order = OrderFixture.createValidOrder();
		var originalAmount = order.getAmount(); // 51.80

		var user = UserFixture.createUserWithCreationDate(LocalDateTime.now().minusYears(1));
		user.setId(1L);

		when(userGateway.findById(1L)).thenReturn(Optional.of(user));

		// Act
		ApplyDiscountUseCase.applyDiscount(order, userGateway);

		// Assert
		assertThat(order.getAmount()).isLessThan(originalAmount);
		// Com 2% de desconto: 51.80 - (51.80 * 0.02) = 50.764
		assertThat(order.getAmount()).isEqualByComparingTo(new BigDecimal("50.7640"));

		verify(userGateway).findById(1L);
	}
}
