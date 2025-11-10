package unit.usecases;

import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Collections;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.soat.fiap.food.core.order.core.application.usecases.EnsureValidOrderItemsUseCase;
import com.soat.fiap.food.core.order.core.domain.exceptions.OrderItemException;
import com.soat.fiap.food.core.order.core.interfaceadapters.gateways.CatalogGateway;

import unit.fixtures.CatalogFixture;
import unit.fixtures.OrderFixture;

@ExtendWith(MockitoExtension.class) @DisplayName("EnsureValidOrderItemsUseCase - Testes Unitários")
class EnsureValidOrderItemsUseCaseTest {

	@Mock
	private CatalogGateway catalogGateway;

	@Test @DisplayName("Deve validar com sucesso quando o item for válido")
	void shouldValidateSuccessfullyWhenAllItemsAreValid() {
		// Arrange
		var catalog = CatalogFixture.createCatalogWithProducts();
		var orderItem = OrderFixture.createOrderItem(catalog.getFirst().name(), catalog.getFirst().id(),
				catalog.getFirst().price());

		when(catalogGateway.findByProductIds(List.of(catalog.getFirst().id()))).thenReturn(catalog);

		// Act & Assert
		assertThatNoException().isThrownBy(() -> EnsureValidOrderItemsUseCase
				.ensureValidOrderItems(Collections.singletonList(orderItem), catalogGateway));

		verify(catalogGateway).findByProductIds(List.of(catalog.getFirst().id()));
	}

	@Test @DisplayName("Deve lançar exceção quando produto não for encontrado")
	void shouldThrowExceptionWhenProductNotFound() {
		// Arrange
		var orderItem = OrderFixture.createBeverageOrderItem();

		when(catalogGateway.findByProductIds(List.of(2L))).thenReturn(Collections.emptyList());

		// Act & Assert
		assertThatThrownBy(() -> EnsureValidOrderItemsUseCase
				.ensureValidOrderItems(Collections.singletonList(orderItem), catalogGateway))
				.isInstanceOf(OrderItemException.class)
				.hasMessage("O produto do item do pedido não existe");

		verify(catalogGateway).findByProductIds(List.of(2L));
	}

	@Test @DisplayName("Deve validar múltiplos itens com sucesso")
	void shouldValidateMultipleItemsSuccessfully() {
		// Arrange
		var productsDto = CatalogFixture.createCatalogWithMultipleProducts();

		var orderItem1 = OrderFixture.createOrderItem(productsDto.getFirst().name(), productsDto.getFirst().id(),
				productsDto.getFirst().price());
		var orderItem2 = OrderFixture.createOrderItem(productsDto.getLast().name(), productsDto.getLast().id(),
				productsDto.getLast().price());
		var orderItems = List.of(orderItem1, orderItem2);

		when(catalogGateway.findByProductIds(List.of(1L, 2L))).thenReturn(productsDto);

		// Act & Assert
		assertThatNoException()
				.isThrownBy(() -> EnsureValidOrderItemsUseCase.ensureValidOrderItems(orderItems, catalogGateway));

		verify(catalogGateway).findByProductIds(List.of(1L, 2L));
	}
}
