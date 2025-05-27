package com.soat.fiap.food.core.api.order.domain.ports.out;

import com.soat.fiap.food.core.api.order.domain.model.Order;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;

import java.util.List;
import java.util.Optional;

/**
 * Porta de saída para persistência de pedidos
 */
public interface OrderRepository {

    /**
     * Salva um pedido
     * @param order Pedido a ser salvo
     * @return Pedido salvo com ID gerado
     */
    Order save(Order order);

    /**
     * Busca um pedido por ID
     * @param id ID do pedido
     * @return Optional contendo o pedido ou vazio se não encontrado
     */
    Optional<Order> findById(Long id);

    /**
     * Busca pedidos por status
     * @param status Status dos pedidos
     * @return Lista de pedidos com o status informado
     */
    List<Order> findByStatus(OrderStatus status);

    /**
     * Busca pedidos de um cliente específico
     * @param userId ID do cliente
     * @return Lista de pedidos do cliente
     */
    List<Order> findByUserId(Long userId);

    /**
     * Lista todos os pedidos
     * @return Lista de pedidos
     */
    List<Order> findAll();

    /**
     * Remove um pedido
     * @param id ID do pedido a ser removido
     */
    void delete(Long id);
} 