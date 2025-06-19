package com.soat.fiap.food.core.api.order.core.application.usecases;

import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderException;
import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import lombok.extern.slf4j.Slf4j;

import java.time.LocalDateTime;

/**
 * Caso de uso: Aplicar desconto baseado no tempo de cadastro do cliente.
 */
@Slf4j
public class ApplyDiscountUseCase {

    /**
     * Aplica um desconto no pedido baseado no tempo de cadastro do cliente.
     * <p>
     * O desconto é calculado como 2% ao ano desde o ano de criação do usuário até o ano atual.
     * O desconto máximo aplicado será menor que 96%.
     * Caso o cliente não seja encontrado, uma exceção {@link OrderException} é lançada.
     *
     * @param order pedido no qual o desconto será aplicado
     * @param gateway Gateway para comunicação com o mundo exterior
     * @throws OrderException se o cliente do pedido não for encontrado
     */
    public static void applyDiscount(Order order, UserGateway gateway) {

        var user = gateway.findById(order.getUserId());

        if (user.isEmpty()) {
            throw new UserNotFoundException("Cliente do pedido não encontrado");
        }

        var yearCreateUser = user.get().getCreatedAt().getYear();
        var currentYear = LocalDateTime.now().getYear();

        var percent = (currentYear - yearCreateUser) * 2;

        if (percent > 0 && percent < 96) {
            order.applyDiscount(percent);
        }

    }

}
