package com.soat.fiap.food.core.api.order.mapper;

import com.soat.fiap.food.core.api.order.application.ports.in.OrderUseCase;
import com.soat.fiap.food.core.api.order.domain.model.Order;
import com.soat.fiap.food.core.api.order.domain.model.OrderItem;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.in.dto.request.CreateOrderRequest;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.in.dto.response.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Componente de mapeamento entre DTOs e objetos de domínio para o contexto de pedidos
 */
@Component
public class OrderDtoMapper {
    
    /**
     * Converte uma requisição de criação de pedido para uma lista de itens de pedido no formato do caso de uso
     * 
     * @param request Requisição de criação de pedido
     * @return Lista de itens de pedido
     */
    public List<OrderUseCase.OrderItemRequest> toOrderItemRequests(CreateOrderRequest request) {
        return request.getItems().stream()
                .map(item -> new OrderUseCase.OrderItemRequest(
                        item.getProductId(),
                        item.getQuantity()))
                .collect(Collectors.toList());
    }
    
    /**
     * Converte um pedido para uma resposta de pedido
     * 
     * @param order Pedido
     * @return Resposta de pedido
     */
    public OrderResponse toResponse(Order order) {
        if (order == null) {
            return null;
        }
        
        return OrderResponse.builder()
                .id(order.getId())
                .orderNumber(order.getOrderNumber())
                .status(order.getOrderStatus())
                .statusDescription(order.getOrderStatus().getDescription())
                .customerId(order.getCustomerId() != null ? order.getCustomerId().getId() : null)
                .customerName(order.getCustomerId() != null ? order.getCustomerId().getName() : null)
                .totalAmount(order.getTotalAmount())
                .items(toOrderItemResponses(order.getOrderItems()))
                .createdAt(order.getCreatedAt())
                .updatedAt(order.getUpdatedAt())
                .build();
    }
    
    /**
     * Converte uma lista de pedidos para uma lista de respostas de pedido
     * 
     * @param orders Lista de pedidos
     * @return Lista de respostas de pedido
     */
    public List<OrderResponse> toResponseList(List<Order> orders) {
        if (orders == null) {
            return List.of();
        }
        
        return orders.stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    /**
     * Converte uma lista de itens de pedido para uma lista de respostas de item de pedido
     * 
     * @param items Lista de itens de pedido
     * @return Lista de respostas de item de pedido
     */
    private List<OrderResponse.OrderItemResponse> toOrderItemResponses(List<OrderItem> items) {
        if (items == null) {
            return List.of();
        }
        
        return items.stream()
                .map(item -> OrderResponse.OrderItemResponse.builder()
                        .id(item.getId())
                        .productId(item.getProductId())
                        .productName(item.getProductName())
                        .quantity(item.getQuantity())
                        .unitPrice(item.getUnitPrice())
                        .subtotal(item.getSubtotal())
                        .build())
                .collect(Collectors.toList());
    }
} 