package com.soat.fiap.food.core.api.order.application.ports.in;

import com.soat.fiap.food.core.api.order.domain.model.Order;
import com.soat.fiap.food.core.api.order.domain.model.OrderStatus;

import java.util.List;
import java.util.Optional;

/**
 * Interface do caso de uso de pedidos
 */
public interface OrderUseCase {
    
    /**
     * Cria um novo pedido
     * 
     * @param customerId ID do cliente (opcional)
     * @param items Lista de IDs de produtos e quantidades
     * @return Pedido criado
     */
    Order createOrder(Long customerId, List<OrderItemRequest> items);
    
    /**
     * Busca um pedido pelo ID
     * 
     * @param orderId ID do pedido
     * @return Pedido encontrado ou vazio
     */
    Optional<Order> findOrderById(Long orderId);
    
    /**
     * Lista todos os pedidos com determinado status
     * 
     * @param status Status dos pedidos
     * @return Lista de pedidos
     */
    List<Order> findOrdersByStatus(OrderStatus status);
    
    /**
     * Atualiza o status de um pedido
     * 
     * @param orderId ID do pedido
     * @param newStatus Novo status
     * @return Pedido atualizado
     */
    Order updateOrderStatus(Long orderId, OrderStatus newStatus);
    
    /**
     * Adiciona um item a um pedido existente
     * 
     * @param orderId ID do pedido
     * @param productId ID do produto
     * @param quantity Quantidade
     * @return Pedido atualizado
     */
    Order addItemToOrder(Long orderId, Long productId, Integer quantity);
    
    /**
     * Classe interna para representar um item de pedido na requisição
     */
    class OrderItemRequest {
        private Long productId;
        private Integer quantity;
        
        public OrderItemRequest(Long productId, Integer quantity) {
            this.productId = productId;
            this.quantity = quantity;
        }
        
        public Long getProductId() {
            return productId;
        }
        
        public Integer getQuantity() {
            return quantity;
        }
    }
} 