package com.soat.fiap.food.core.api.order.core.application.usecases;

import com.soat.fiap.food.core.api.order.core.application.inputs.CreateOrderInput;
import com.soat.fiap.food.core.api.order.core.application.inputs.mappers.CreateOrderMapper;
import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.gateways.OrderGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Caso de uso: Criar pedido.
 */
@Slf4j
public class CreateOrderUseCase {

    /**
     * Cria um novo pedido
     *
     * @param createOrderInput dados de criação do pedido
     * @param gateway Gateway para comunicação com o mundo exterior
     * @return Pedido criado
     */
    public static Order createOrder(CreateOrderInput createOrderInput, OrderGateway gateway) {

        log.info("Criando novo pedido para o cliente ID: {}", createOrderInput.userId());

        var order = CreateOrderMapper.toDomain(createOrderInput);

        AssignGuestUserToOrderUseCase.assignGuestUserToOrder(order);

        EnsureValidOrderItemsUseCase.ensureValidOrderItems(order.getOrderItems());

        ApplyDiscountUseCase.applyDiscount(order);

        return order;
    }
}
