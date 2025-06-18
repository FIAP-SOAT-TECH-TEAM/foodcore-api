package com.soat.fiap.food.core.api.order.core.interfaceadapters.gateways;

import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.OrderDataSource;

import java.util.List;
import java.util.Optional;

/**
 * Gateway para persistência do agregado Pedido.
 */
public class OrderGateway {

    private final OrderDataSource orderDataSource;

    public OrderGateway(OrderDataSource orderDataSource) {
        this.orderDataSource = orderDataSource;
    }

    /**
     * Salva o agregado Pedido.
     *
     * @param order Agregado Pedido a ser salvo
     * @return Pedido salvo com identificadores atualizados
     */
    public Order save(Order order) {
        return orderDataSource.save(order);
    }

    /**
     * Busca um pedido pelo ID.
     *
     * @param id ID do pedido
     * @return Optional contendo o pedido ou vazio se não encontrado
     */
    public Optional<Order> findById(Long id) {
        return orderDataSource.findById(id);
    }

    /**
     * Lista todos os pedidos persistidos.
     *
     * @return Lista de pedidos
     */
    public List<Order> findAll() {
        return orderDataSource.findAll();
    }

    /**
     * Remove um pedido com base em seu ID.
     *
     * @param id ID do pedido a ser removido
     */
    public void delete(Long id) {
        orderDataSource.delete(id);
    }

    /**
     * Busca todos os pedidos com um determinado status.
     *
     * @param status Status do pedido
     * @return Lista de pedidos com o status informado
     */
    public List<Order> findByOrderStatus(OrderStatus status) {
        return orderDataSource.findByOrderStatus(status);
    }

    /**
     * Busca todos os pedidos de um determinado cliente.
     *
     * @param userId ID do cliente
     * @return Lista de pedidos do cliente
     */
    public List<Order> findByUserId(Long userId) {
        return orderDataSource.findByUserId(userId);
    }

    /**
     * Busca pedidos ativos, ordenados por prioridade de status e data de criação.
     *
     * Prioridade de status: PRONTO > EM_PREPARACAO > RECEBIDO.
     * Pedidos com status FINALIZADO não são incluídos.
     *
     * @return Lista de pedidos ativos ordenados
     */
    public List<Order> findActiveOrdersSorted() {
        return orderDataSource.findActiveOrdersSorted();
    }
}