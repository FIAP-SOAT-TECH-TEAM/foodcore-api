package com.soat.fiap.food.core.api.order.core.domain.vo;

import java.io.Serializable;
import java.math.BigDecimal;

import org.apache.commons.lang3.Validate;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

/**
 * Value Object que representa o preço do item de um pedido
 */
@Embeddable
public class OrderItemPrice implements Serializable {

	@Column(name = "quantity", nullable = false)
	private int quantity;

	@Column(name = "unit_price", nullable = false, precision = 10, scale = 2)
	private BigDecimal unitPrice;

	protected OrderItemPrice() {
		// construtor default para o JPA
	}

	public OrderItemPrice(int quantity, BigDecimal unitPrice) {
		validate(quantity, unitPrice);
		this.quantity = quantity;
		this.unitPrice = unitPrice;
	}

	public BigDecimal getSubTotal() {
		return unitPrice.multiply(BigDecimal.valueOf(quantity));
	}

	public int getQuantity() {
		return quantity;
	}

	public BigDecimal getUnitPrice() {
		return unitPrice;
	}

	private void validate(int quantity, BigDecimal unitPrice) {
		Validate.isTrue(quantity > 0, "A quantidade do item deve ser maior que 0");
		Validate.isTrue(unitPrice.compareTo(BigDecimal.ZERO) > 0, "O preço unitário do item deve ser maior que 0");
	}
}
