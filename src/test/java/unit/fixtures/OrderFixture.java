package unit.fixtures;

import java.math.BigDecimal;
import java.util.List;

import com.soat.fiap.food.core.order.core.application.inputs.CreateOrderInput;
import com.soat.fiap.food.core.order.core.application.inputs.OrderItemInput;
import com.soat.fiap.food.core.order.core.domain.model.Order;
import com.soat.fiap.food.core.order.core.domain.model.OrderItem;
import com.soat.fiap.food.core.order.core.domain.vo.OrderItemPrice;

/**
 * Fixture para criação de objetos do módulo Order para testes unitários
 */
public class OrderFixture {

	public static Order createValidOrder() {
		var orderItems = List.of(createValidOrderItem());
		return new Order("A23basb3u123", orderItems);
	}

	public static Order createOrderWithMultipleItems() {
		var orderItems = List.of(createValidOrderItem(), createBeverageOrderItem(), createDessertOrderItem());
		return new Order("A23basb3u123", orderItems);
	}

	public static void createOrderWithoutUser() {
		var orderItems = List.of(createValidOrderItem());
		new Order(null, orderItems);
	}

	public static OrderItem createValidOrderItem() {
		return new OrderItem(1L, "Big Mac", new OrderItemPrice(2, new BigDecimal("25.90")), null);
	}

	public static OrderItem createBeverageOrderItem() {
		return new OrderItem(2L, "Coca-Cola", new OrderItemPrice(1, new BigDecimal("8.50")), "Sem gelo");
	}

	public static OrderItem createOrderItem(String name, Long id, BigDecimal price) {
		return new OrderItem(id, name, new OrderItemPrice(1, price), "Sem gelo");
	}

	public static OrderItem createDessertOrderItem() {
		return new OrderItem(3L, "Torta de Chocolate", new OrderItemPrice(1, new BigDecimal("18.90")), "Aquecida");
	}

	public static OrderItem createExpensiveOrderItem() {
		return new OrderItem(4L, "Combo Premium", new OrderItemPrice(1, new BigDecimal("45.00")), null);
	}

	public static OrderItem createOrderItemWithObservations() {
		return new OrderItem(2L, "Batata Frita G", new OrderItemPrice(1, new BigDecimal("15.00")), "Sem sal");
	}

	public static Order createOrderWithSingleItem() {
		var orderItems = List.of(createValidOrderItem());
		return new Order("A23basb3u123", orderItems);
	}

	public static CreateOrderInput createValidCreateOrderInput() {
		var orderItems = List.of(createValidOrderItemInput());
		return new CreateOrderInput("A23basb3u123", orderItems);
	}

	public static CreateOrderInput createCreateOrderInputWithMultipleItems() {
		var orderItems = List.of(createValidOrderItemInput(), createBeverageOrderItemInput(),
				createDessertOrderItemInput());
		return new CreateOrderInput("A23basb3u123", orderItems);
	}

	public static OrderItemInput createValidOrderItemInput() {
		return new OrderItemInput(1L, "Big Mac", 2, new BigDecimal("25.90"), null);
	}

	public static OrderItemInput createBeverageOrderItemInput() {
		return new OrderItemInput(2L, "Coca-Cola", 1, new BigDecimal("8.50"), "Sem gelo");
	}

	public static OrderItemInput createDessertOrderItemInput() {
		return new OrderItemInput(3L, "Torta de Chocolate", 1, new BigDecimal("18.90"), "Aquecida");
	}
}
