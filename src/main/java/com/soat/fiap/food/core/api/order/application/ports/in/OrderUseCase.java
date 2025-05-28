package com.soat.fiap.food.core.api.order.application.ports.in;

import com.soat.fiap.food.core.api.order.application.dto.request.CreateOrderRequest;
import com.soat.fiap.food.core.api.order.application.dto.response.OrderResponse;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;

import java.util.List;

/**
 * Interface do caso de uso de pedidos
 */
public interface OrderUseCase {

    /**
     * Cria um novo pedido
     *
     * @param createOrderRequest dados de criação do pedido
     * @return Pedido criado
     */
    OrderResponse createOrder(CreateOrderRequest createOrderRequest);

//    /**
//     * Busca um pedido pelo ID
//     *
//     * @param orderId ID do pedido
//     * @return Pedido encontrado ou vazio
//     */
//    OrderResponse findOrderById(Long orderId);
//
//    /**
//     * Lista todos os pedidos com determinado status
//     *
//     * @param status Status dos pedidos
//     * @return Lista de pedidos
//     */
//    List<OrderResponse> findOrdersByStatus(OrderStatus status);
//
        /**
         * Atualiza o status de um pedido
         *
         * @param orderId ID do pedido
         * @param newStatus Novo status
         */
        void updateOrderStatus(Long orderId, OrderStatus newStatus);
//
//    /**
//     * Adiciona um item a um pedido existente
//     *
//     * @param orderId ID do pedido
//     * @param productId ID do produto
//     * @param quantity Quantidade
//     * @return Pedido atualizado
//     */
//    OrderResponse addItemToOrder(Long orderId, Long productId, Integer quantity);
} 