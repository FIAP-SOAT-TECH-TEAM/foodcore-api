package com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.soat.fiap.food.core.api.order.core.domain.model.Order;
import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.entity.OrderEntity;
import com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.mapper.OrderEntityMapper;

/**
 * Implementação concreta: DataSource para persistência do agregado Pedido.
 */
@Component
public class PostgresOrderDataSource implements OrderDataSource {

	private final SpringDataOrderRepository springDataOrderRepository;
	private final OrderEntityMapper orderEntityMapper;

	public PostgresOrderDataSource(SpringDataOrderRepository springDataOrderRepository,
			OrderEntityMapper orderEntityMapper) {
		this.springDataOrderRepository = springDataOrderRepository;
		this.orderEntityMapper = orderEntityMapper;
	}

	@Override @Transactional
	public Order save(Order order) {
		OrderEntity orderEntity = orderEntityMapper.toEntity(order);
		OrderEntity saved = springDataOrderRepository.save(orderEntity);
		return orderEntityMapper.toDomain(saved);
	}

	@Override @Transactional(readOnly = true)
	public Optional<Order> findById(Long id) {
		return springDataOrderRepository.findById(id).map(orderEntityMapper::toDomain);
	}

	@Override @Transactional(readOnly = true)
	public List<Order> findByOrderStatus(OrderStatus status) {
		List<OrderEntity> orderEntities = springDataOrderRepository.findByOrderStatus(status);
		return orderEntityMapper.toDomainList(orderEntities);
	}

	@Override @Transactional(readOnly = true)
	public List<Order> findByUserId(Long customerId) {
		List<OrderEntity> orderEntities = springDataOrderRepository.findByUserId(customerId);
		return orderEntityMapper.toDomainList(orderEntities);
	}

	@Override @Transactional(readOnly = true)
	public List<Order> findAll() {
		List<OrderEntity> orderEntities = springDataOrderRepository.findAll();
		return orderEntityMapper.toDomainList(orderEntities);
	}

	@Override @Transactional
	public void delete(Long id) {
		springDataOrderRepository.deleteById(id);
	}

	@Override @Transactional(readOnly = true)
	public List<Order> findActiveOrdersSorted() {
		List<OrderEntity> orderEntities = springDataOrderRepository.findActiveOrdersSorted();
		return orderEntityMapper.toDomainList(orderEntities);
	}
}
