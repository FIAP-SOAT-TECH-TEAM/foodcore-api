package com.soat.fiap.food.core.api.order.core.application.usecases;

import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.response.OrderResponse;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Caso de uso: Buscar pedidos ativos ordenados por prioridade e data de criação.
 */
@Slf4j
public class GetActiveOrdersSortedUseCase {

    /**
     * Busca pedidos que não estejam finalizados, ordenados por prioridade de status e data de criação.
     * A ordem de prioridade de status é: PRONTO > EM_PREPARACAO > RECEBIDO.
     * Pedidos com status FINALIZADO não são retornados.
     *
     */
    public List<OrderResponse> getActiveOrdersSorted() {
        logger.info("Buscando pedidos ativos ordenados por prioridade e data de criação.");

        var activeOrders = dataSource.findActiveOrdersSorted();

        return activeOrders.stream()
                .map(orderResponseMapper::toResponse)
                .toList();
    }
}
