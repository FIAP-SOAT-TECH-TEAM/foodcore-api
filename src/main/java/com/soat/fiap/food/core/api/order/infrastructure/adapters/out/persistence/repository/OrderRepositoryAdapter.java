package com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.repository;

import com.soat.fiap.food.core.api.order.domain.model.Order;
import com.soat.fiap.food.core.api.order.domain.ports.out.OrderRepository;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.entity.OrderEntity;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.out.persistence.mapper.OrderEntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

/**
 * Adaptador que implementa a porta de saída OrderRepository
 */
@Component
public class OrderRepositoryAdapter implements OrderRepository {

    private final SpringDataOrderRepository springDataOrderRepository;
    private final OrderEntityMapper orderEntityMapper;

    public OrderRepositoryAdapter(
            SpringDataOrderRepository springDataOrderRepository,
            OrderEntityMapper orderEntityMapper) {
        this.springDataOrderRepository = springDataOrderRepository;
        this.orderEntityMapper = orderEntityMapper;
    }

    @Override
    public Order save(Order order) {
        OrderEntity orderEntity = orderEntityMapper.toEntity(order);
        OrderEntity saved = springDataOrderRepository.save(orderEntity);
        return orderEntityMapper.toDomain(saved);
    }

    @Override
    public Optional<Order> findById(Long id) {
        return springDataOrderRepository.findById(id)
                .map(orderEntityMapper::toDomain);
    }

    @Override
    public List<Order> findByStatus(OrderStatus status) {
        List<OrderEntity> orderEntities = springDataOrderRepository.findByStatus(status);
        return orderEntityMapper.toDomainList(orderEntities);
    }

    @Override
    public List<Order> findByUserId(Long customerId) {
        List<OrderEntity> orderEntities = springDataOrderRepository.findByUserId(customerId);
        return orderEntityMapper.toDomainList(orderEntities);
    }

    @Override
    public List<Order> findAll() {
        List<OrderEntity> orderEntities = springDataOrderRepository.findAll();
        return orderEntityMapper.toDomainList(orderEntities);
    }

    @Override
    public void delete(Long id) {
        springDataOrderRepository.deleteById(id);
    }
} 