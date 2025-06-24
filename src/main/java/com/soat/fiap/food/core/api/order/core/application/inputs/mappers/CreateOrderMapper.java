package com.soat.fiap.food.core.api.order.core.application.inputs.mappers;

import java.util.stream.Collectors;

import com.soat.fiap.food.core.api.order.core.application.inputs.CreateOrderInput;
import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.request.CreateOrderRequest;

/**
 * Classe utilitária responsável por mapear objetos entre
 * {@link CreateOrderRequest} e {@link CreateOrderInput}.
 */
public class CreateOrderMapper {

	/**
	 * Converte um {@link CreateOrderRequest} (camada web.api) em um
	 * {@link CreateOrderInput} (camada de aplicação).
	 *
	 * @param request
	 *            O DTO da requisição HTTP para criação de pedido.
	 * @return Um objeto {@link CreateOrderInput} com os dados para processar a
	 *         criação do pedido.
	 */
	public static CreateOrderInput toInput(CreateOrderRequest request) {
		return new CreateOrderInput(request.getUserId(),
				request.getItems().stream().map(OrderItemMapper::toInput).collect(Collectors.toList()));
	}

	/**
	 * Converte um {@link CreateOrderInput} (camada de aplicação) em uma entidade de
	 * domínio {@link Order}.
	 *
	 * @param input
	 *            O DTO de entrada da aplicação.
	 * @return Uma entidade {@link Order} representando o modelo de domínio para
	 *         persistência e regras de negócio.
	 */
	public static Order toDomain(CreateOrderInput input) {
		return new Order(input.userId(),
				input.items().stream().map(OrderItemMapper::toDomain).collect(Collectors.toList()));
	}
}
