package com.soat.fiap.food.core.api.payment.infrastructure.out.persistence.postgres.repository;

import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.payment.infrastructure.out.persistence.postgres.mapper.PaymentEntityMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Implementação concreta: DataSource para persistência do agregado Pagamento.
 */
@Component
public class PostgresPaymentDataSource implements PaymentDataSource {

    private final SpringDataPaymentRepository repository;
    private final PaymentEntityMapper mapper;

    public PostgresPaymentDataSource(SpringDataPaymentRepository repository, PaymentEntityMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    @Override
    @Transactional
    public Payment save(Payment payment) {
        var entity = mapper.toEntity(payment);
        var savedEntity = repository.save(entity);
        return mapper.toDomain(savedEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Payment> findTopByOrderIdOrderByIdDesc(Long orderId) {
        return repository.findTopByOrderIdOrderByIdDesc(orderId)
                .map(mapper::toDomain);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByOrderId(Long orderId) {
        return repository.existsByOrderId(orderId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Payment> findExpiredPaymentsWithoutApprovedOrCancelled(LocalDateTime now) {
      var paymentEntities = repository.findExpiredPaymentsWithoutApprovedOrCancelled(now);

      return mapper.toDomainList(paymentEntities);
    }
}