package com.soat.fiap.food.core.api.order.application.services;

import com.soat.fiap.food.core.api.customer.application.ports.out.CustomerRepository;
import com.soat.fiap.food.core.api.customer.domain.model.Customer;
import com.soat.fiap.food.core.api.order.application.ports.in.OrderUseCase;
import com.soat.fiap.food.core.api.order.application.ports.out.OrderRepository;
import com.soat.fiap.food.core.api.order.domain.events.OrderCreatedEvent;
import com.soat.fiap.food.core.api.order.domain.model.Order;
import com.soat.fiap.food.core.api.order.domain.model.OrderItem;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.product.application.ports.out.ProductRepository;
import com.soat.fiap.food.core.api.product.domain.model.Product;
import com.soat.fiap.food.core.api.shared.exception.ResourceNotFoundException;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
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
    private final CustomerRepository customerRepository;
    private final CustomLogger logger;
    
    public OrderService(
            ApplicationEventPublisher eventPublisher,
            OrderRepository orderRepository,
            ProductRepository productRepository,
            CustomerRepository customerRepository) {
        this.eventPublisher = eventPublisher;
        this.orderRepository = orderRepository;
        this.productRepository = productRepository;
        this.customerRepository = customerRepository;
        this.logger = CustomLogger.getLogger(getClass());
    }
    
    @Override
    @Transactional
    public Order createOrder(Long customerId, List<OrderItemRequest> items) {
        logger.info("Criando novo pedido para o cliente ID: {}", customerId);
        
        Customer customer = null;
        if (customerId != null) {
            customer = customerRepository.findById(customerId)
                    .orElseThrow(() -> new ResourceNotFoundException("customerId", customerId));
        }
        
        Order order = Order.builder()
                .orderNumber(generateOrderNumber())
                .status(OrderStatus.PENDING)
                .customerId(customer)
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
        
        if (customerId != null) {
            customerRepository.findById(customerId).ifPresent(fullCustomer -> {
                savedOrder.setCustomerId(fullCustomer);
                logger.debug("Cliente carregado explicitamente: {}", fullCustomer.getName());
            });
        }
        
        eventPublisher.publishEvent(
            OrderCreatedEvent.of(
                savedOrder.getId(),
                savedOrder.getTotalAmount(),
                savedOrder.getOrderStatus(),
                customerId
            )
        );
        
        logger.info("Pedido {} criado com sucesso. Total: {}", savedOrder.getId(), savedOrder.getTotalAmount());
        return savedOrder;
    }
    
    @Override
    public Optional<Order> findOrderById(Long orderId) {
        logger.debug("Buscando pedido ID: {}", orderId);
        Optional<Order> orderOpt = orderRepository.findById(orderId);
        
        orderOpt.ifPresent(this::loadCustomerIfNeeded);
        
        return orderOpt;
    }
    
    @Override
    public List<Order> findOrdersByStatus(OrderStatus status) {
        logger.debug("Buscando pedidos com status: {}", status);
        List<Order> orders = orderRepository.findByStatus(status);
        
        orders.forEach(this::loadCustomerIfNeeded);
        
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
     * Carrega informações do cliente se necessário
     */
    private void loadCustomerIfNeeded(Order order) {
        if (order == null) {
            return;
        }
        
        boolean needsCustomerData = (order.getCustomerId() == null && order.getCustomerId() != null) ||
                                   (order.getCustomerId() != null && order.getCustomerId().getName() == null);
        
        if (needsCustomerData && order.getCustomerId() != null) {
            logger.debug("Carregando dados do cliente ID: {} para pedido ID: {}", 
                        order.getCustomerId(), order.getId());
            
            customerRepository.findById(order.getCustomerId())
                    .ifPresent(customer -> {
                        order.setCustomerId(customer);
                        logger.debug("Cliente carregado: {}", customer.getName());
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