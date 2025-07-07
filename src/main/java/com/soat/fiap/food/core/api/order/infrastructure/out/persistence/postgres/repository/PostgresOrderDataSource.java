package com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.dto.OrderDTO;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.entity.OrderEntity;
import com.soat.fiap.food.core.api.order.infrastructure.out.persistence.postgres.mapper.OrderEntityMapper;
import com.soat.fiap.food.core.api.shared.infrastructure.common.mapper.CycleAvoidingMappingContext;

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
	public OrderDTO save(OrderDTO orderDTO) {
		CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();
		OrderEntity orderEntity = orderEntityMapper.toEntity(orderDTO, context);
		OrderEntity savedEntity = springDataOrderRepository.save(orderEntity);
		return orderEntityMapper.toDTO(savedEntity, context);
	}

	@Override @Transactional(readOnly = true)
	public Optional<OrderDTO> findById(Long id) {
		CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();
		return springDataOrderRepository.findById(id).map(entity -> orderEntityMapper.toDTO(entity, context));
	}

	@Override @Transactional(readOnly = true)
	public List<OrderDTO> findByOrderStatus(OrderStatus status) {
		CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();
		List<OrderEntity> orderEntities = springDataOrderRepository.findByOrderStatus(status);
		return orderEntities.stream().map(entity -> orderEntityMapper.toDTO(entity, context)).toList();
	}

	@Override @Transactional(readOnly = true)
	public List<OrderDTO> findByUserId(Long userId) {
		CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();
		List<OrderEntity> orderEntities = springDataOrderRepository.findByUserId(userId);
		return orderEntities.stream().map(entity -> orderEntityMapper.toDTO(entity, context)).toList();
	}

	@Override @Transactional(readOnly = true)
	public List<OrderDTO> findAll() {
		CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();
		List<OrderEntity> orderEntities = springDataOrderRepository.findAll();
		return orderEntities.stream().map(entity -> orderEntityMapper.toDTO(entity, context)).toList();
	}

	@Override @Transactional
	public void delete(Long id) {
		springDataOrderRepository.deleteById(id);
	}

	@Override @Transactional(readOnly = true)
	public List<OrderDTO> findActiveOrdersSorted() {
		CycleAvoidingMappingContext context = new CycleAvoidingMappingContext();
		List<OrderEntity> orderEntities = springDataOrderRepository.findActiveOrdersSorted();
		return orderEntities.stream().map(entity -> orderEntityMapper.toDTO(entity, context)).toList();
	}
}
