package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.persistence.repository;

import com.soat.fiap.food.core.api.payment.domain.ports.out.PaymentRepository;
import org.springframework.stereotype.Component;

/**
 * Adaptador que implementa a interface do repositório de pagamentos
 * Faz a ponte entre o domínio e a infraestrutura de persistência
 */
@Component
public class PaymentRepositoryAdapter implements PaymentRepository {
//
//    private final SpringDataPaymentRepository repository;
//    private final PaymentEntityMapper mapper;
//
//    public PaymentRepositoryAdapter(SpringDataPaymentRepository repository, PaymentEntityMapper mapper) {
//        this.repository = repository;
//        this.mapper = mapper;
//    }
//
//    @Override
//    public Payment save(Payment payment) {
//        var entity = mapper.toEntity(payment);
//        var savedEntity = repository.save(entity);
//        return mapper.toDomain(savedEntity);
//    }
//
//    @Override
//    public Optional<Payment> findById(Long id) {
//        return repository.findById(id)
//                .map(mapper::toDomain);
//    }
//
//    @Override
//    public Optional<Payment> findByExternalId(String externalId) {
//        return repository.findByExternalId(externalId)
//                .map(mapper::toDomain);
//    }
//
//    @Override
//    public Optional<Payment> findByOrderId(Long orderId) {
//        return repository.findByOrderId(orderId)
//                .map(mapper::toDomain);
//    }
//
//    @Override
//    public List<Payment> findByStatus(OrderPaymentStatus status) {
//        List<PaymentEntity> entities = repository.findByStatus(status);
//        return mapper.toDomainList(entities);
//    }
}