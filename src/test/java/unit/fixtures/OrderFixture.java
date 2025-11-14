package unit.fixtures;

import java.math.BigDecimal;
import java.util.List;

import com.soat.fiap.food.core.order.core.application.inputs.CreateOrderInput;
import com.soat.fiap.food.core.order.core.application.inputs.OrderItemInput;
import com.soat.fiap.food.core.order.core.domain.model.Order;
import com.soat.fiap.food.core.order.core.domain.model.OrderItem;
import com.soat.fiap.food.core.order.core.domain.vo.OrderItemPrice;
import com.soat.fiap.food.core.order.infrastructure.in.web.api.dto.request.CreateOrderRequest;
import com.soat.fiap.food.core.order.infrastructure.in.web.api.dto.request.OrderItemRequest;

/**
 * Fixture para criação de objetos do módulo Order para testes unitários
 */
public class OrderFixture {

	private static OrderItemPrice price(BigDecimal unitPrice) {
		return new OrderItemPrice(1, unitPrice);
	}

	private static OrderItemPrice price(int quantity, BigDecimal unitPrice) {
		return new OrderItemPrice(quantity, unitPrice);
	}

	private static OrderItemPrice validBurgerPrice() {
		return new OrderItemPrice(2, new BigDecimal("25.90"));
	}

	private static OrderItemPrice beveragePrice() {
		return new OrderItemPrice(1, new BigDecimal("8.50"));
	}

	private static OrderItemPrice dessertPrice() {
		return new OrderItemPrice(1, new BigDecimal("18.90"));
	}

	private static OrderItemPrice expensivePrice() {
		return new OrderItemPrice(1, new BigDecimal("45.00"));
	}

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
		return new OrderItem(1L, "Big Mac", validBurgerPrice(), null);
	}

	public static OrderItem createBeverageOrderItem() {
		return new OrderItem(2L, "Coca-Cola", beveragePrice(), "Sem gelo");
	}

	public static OrderItem createDessertOrderItem() {
		return new OrderItem(3L, "Torta de Chocolate", dessertPrice(), "Aquecida");
	}

	public static OrderItem createExpensiveOrderItem() {
		return new OrderItem(4L, "Combo Premium", expensivePrice(), null);
	}

	public static OrderItem createOrderItem(String name, Long id, BigDecimal priceValue) {
		return new OrderItem(id, name, price(priceValue), "Sem gelo");
	}

	public static OrderItem createOrderItem(String name, Long id, BigDecimal priceValue, int quantity) {
		return new OrderItem(id, name, price(quantity, priceValue), "Sem gelo");
	}

	public static OrderItem createOrderItemWithObservations() {
		return new OrderItem(2L, "Batata Frita G", price(new BigDecimal("15.00")), "Sem sal");
	}

	public static Order createOrderWithSingleItem() {
		var orderItems = List.of(createValidOrderItem());
		return new Order("A23basb3u123", orderItems);
	}

	public static CreateOrderInput createValidCreateOrderInput() {
		return new CreateOrderInput("A23basb3u123", List.of(createValidOrderItemInput()));
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

	public static OrderItemRequest createValidOrderItemRequest() {
		return new OrderItemRequest(1L, "X-Burger", 2, new BigDecimal("22.90"), "Sem cebola");
	}

	public static CreateOrderRequest createValidCreateOrderRequest() {
		return new CreateOrderRequest(List.of(createValidOrderItemRequest()));
	}

}
