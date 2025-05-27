package com.soat.fiap.food.core.api.order.application.services;

import com.soat.fiap.food.core.api.order.application.ports.in.OrderUseCase;
import com.soat.fiap.food.core.api.order.application.ports.out.OrderRepository;
import com.soat.fiap.food.core.api.order.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.order.domain.model.Order;
import com.soat.fiap.food.core.api.order.domain.model.OrderItem;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.catalog.domain.model.Product;
import com.soat.fiap.food.core.api.shared.exception.ResourceNotFoundException;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import com.soat.fiap.food.core.api.user.application.ports.out.UserRepository;
import com.soat.fiap.food.core.api.user.domain.model.User;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Implementação do caso de uso de pedidos
 */
@Service
public class OrderService implements OrderUseCase {
    
    private final ApplicationEventPublisher eventPublisher;
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final CustomLogger logger;
    
    public OrderService(
            ApplicationEventPublisher eventPublisher,
            OrderRepository orderRepository,
            ProductRepository productRepository,
            UserRepository userRepository) {
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.logger = CustomLogger.getLogger(getClass());
    }
    
    @Override
    @Transactional
    public Order createOrder(Long userId, List<OrderItemRequest> items) {
        logger.info("Criando novo pedido para o usuário ID: {}", userId);
        
        User user = null;
        if (userId != null) {
            user = userRepository.findById(userId)
                    .orElseThrow(() -> new ResourceNotFoundException("userId", userId));
        }
        
        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .status(OrderStatus.RECEIVED)
                .userId(user)
                .orderItems(new ArrayList<>())
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
        
        for (OrderItemRequest itemRequest : items) {
            Product product = productRepository.findById(itemRequest.getProductId())
                    .orElseThrow(() -> new ResourceNotFoundException("product", itemRequest.getProductId()));
            
            OrderItem orderItem = OrderItem.builder()
                    .product(product)
                    .quantity(itemRequest.getQuantity())
                    .unitPrice(product.getPrice())
                    .observations("")
                    .build();
            
            order.addItem(orderItem);
        }
        
        Order savedOrder = orderRepository.save(order);
        
        if (userId != null) {
            userRepository.findById(userId).ifPresent(fullUser -> {
                savedOrder.setUserId(fullUser.getId());
                logger.debug("Usuário carregado explicitamente: {}", fullUser.getName());
            });
        }
        
        eventPublisher.publishEvent(
            OrderCreatedEvent.of(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getOrderStatus(),
                userId
            )
        );
        
        logger.info("Pedido {} criado com sucesso. Total: {}", savedOrder.getId(), savedOrder.getTotalAmount());
        return savedOrder;
    }
    
    @Override
    public Optional<Order> findOrderById(Long orderId) {
        logger.debug("Buscando pedido ID: {}", orderId);
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        
        orderOpt.ifPresent(this::loadUserIfNeeded);
        
        return orderOpt;
    }
    
    @Override
    public List<Order> findOrdersByStatus(OrderStatus status) {
        logger.debug("Buscando pedidos com status: {}", status);
        List<Order> orders = orderRepository.findByStatus(status);
        
        orders.forEach(this::loadUserIfNeeded);
        
        return orders;
    }
    
    @Override
    @Transactional
    public Order updateOrderStatus(Long orderId, OrderStatus newStatus) {
        logger.info("Atualizando status do pedido {} para {}", orderId, newStatus);
        
        Order order = findOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("order", orderId));
        
        order.updateStatus(newStatus);
        
        Order updatedOrder = orderRepository.save(order);
        
        logger.info("Status do pedido {} atualizado para {}", orderId, newStatus);
        return updatedOrder;
    }
    
    @Override
    @Transactional
    public Order addItemToOrder(Long orderId, Long productId, Integer quantity) {
        logger.info("Adicionando item ao pedido {}: produto {}, quantidade {}", orderId, productId, quantity);
        
        Order order = findOrderById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("order", orderId));
        
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new ResourceNotFoundException("product", productId));
        
        OrderItem item = OrderItem.builder()
                .product(product)
                .quantity(quantity)
                .unitPrice(product.getPrice())
                .build();
        
        order.addItem(item);
        
        Order updatedOrder = orderRepository.save(order);
        
        logger.info("Item adicionado ao pedido {}. Novo total: {}", orderId, updatedOrder.getTotalAmount());
        return updatedOrder;
    }
    
    /**
     * Carrega informações do usuário se necessário
     */
    private void loadUserIfNeeded(Order order) {
        if (order == null) {
            return;
        }
        
        boolean needsUserData = (order.getUserId() == null && order.getUserId() != null) ||
                                   (order.getUserId() != null && order.getUserId().getName() == null);
        
        if (needsUserData && order.getUserId() != null) {
            logger.debug("Carregando dados do usuário ID: {} para pedido ID: {}",
                        order.getUserId(), order.getId());
            
            userRepository.findById(order.getUserId())
                    .ifPresent(user -> {
                        order.setUserId(user.getId());
                        logger.debug("Usuário carregado: {}", user.getName());
                    });
        }
    }
    
    /**
     * Gera um número de pedido único
     */
    private String generateOrderNumber() {
        return "ORD-" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }
} 