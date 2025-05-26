package com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence;

import com.soat.fiap.food.core.api.order.domain.ports.out.OrderRepository;
import com.soat.fiap.food.core.api.order.domain.model.Order;
import com.soat.fiap.food.core.api.order.domain.model.OrderItem;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.entity.OrderEntity;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.entity.OrderItemEntity;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.mapper.OrderEntityMapper;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.mapper.OrderItemEntityMapper;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.repository.SpringDataOrderRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador que implementa a porta de saída OrderRepository
 */
@Component
public class OrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderRepository springDataOrderRepository;
    private final OrderEntityMapper orderEntityMapper;
    private final OrderItemEntityMapper orderItemEntityMapper;

    public OrderRepositoryAdapter(
            SpringDataOrderRepository springDataOrderRepository,
            OrderEntityMapper orderEntityMapper,
            OrderItemEntityMapper orderItemEntityMapper) {
        this.springDataOrderRepository = springDataOrderRepository;
        this.orderEntityMapper = orderEntityMapper;
        this.orderItemEntityMapper = orderItemEntityMapper;
    }

    @Override
    @Transactional
    public Order save(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        
        if (order.getOrderItems() != null) {
            orderEntity.setItems(new java.util.ArrayList<>());
            for (OrderItem item : order.getOrderItems()) {
                OrderItemEntity itemEntity = orderItemEntityMapper.toEntity(item);
                orderEntity.addItem(itemEntity);
            }
        }
        
        OrderEntity savedEntity = springDataOrderRepository.save(orderEntity);
        Order savedOrder = orderEntityMapper.toDomain(savedEntity);
        
        if (order.getCustomerId() != null && order.getCustomerId().getName() != null) {
            savedOrder.setCustomerId(order.getCustomerId());
        }
        
        return savedOrder;
    }

    @Override
    public Optional<Order> findById(Long id) {
        return springDataOrderRepository.findById(id)
                .map(orderEntityMapper::toDomain);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        OrderEntity.OrderStatusEntity statusEntity = orderEntityMapper.statusToDatabaseValue(status);
        List<OrderEntity> orderEntities = springDataOrderRepository.findByStatus(statusEntity);
        return orderEntityMapper.toDomainList(orderEntities);
    }

    @Override
    public List<Order> findByCustomerId(Long customerId) {
        List<OrderEntity> orderEntities = springDataOrderRepository.findByCustomerId(customerId);
        return orderEntityMapper.toDomainList(orderEntities);
    }

    @Override
    public List<Order> findAll() {
        List<OrderEntity> orderEntities = springDataOrderRepository.findAll();
        return orderEntityMapper.toDomainList(orderEntities);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        springDataOrderRepository.deleteById(id);
    }
} 