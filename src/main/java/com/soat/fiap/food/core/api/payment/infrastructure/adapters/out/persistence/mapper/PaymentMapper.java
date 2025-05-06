package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.persistence.mapper;

import com.soat.fiap.food.core.api.payment.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.persistence.entity.PaymentEntity;
import org.springframework.stereotype.Component;

/**
 * Mapeador entre entidade JPA e modelo de domínio de pagamento
 */
@Component
public class PaymentMapper {
    
    /**
     * Converte modelo de domínio para entidade JPA
     * 
     * @param payment Pagamento (modelo de domínio)
     * @return Entidade JPA
     */
    public PaymentEntity toEntity(Payment payment) {
        if (payment == null) {
            return null;
        }
        
        return PaymentEntity.builder()
                .id(payment.getId())
                .orderId(payment.getOrderId())
                .externalId(payment.getExternalId())
                .amount(payment.getAmount())
                .method(payment.getMethod())
                .status(payment.getStatus())
                .qrCodeUrl(payment.getQrCodeUrl())
                .qrCodeData(payment.getQrCodeData())
                .createdAt(payment.getCreatedAt())
                .updatedAt(payment.getProcessedAt())
                .build();
    }
    
    /**
     * Converte entidade JPA para modelo de domínio
     * 
     * @param entity Entidade JPA
     * @return Pagamento (modelo de domínio)
     */
    public Payment toDomain(PaymentEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return Payment.builder()
                .id(entity.getId())
                .orderId(entity.getOrderId())
                .externalId(entity.getExternalId())
                .amount(entity.getAmount())
                .method(entity.getMethod())
                .status(entity.getStatus())
                .qrCodeUrl(entity.getQrCodeUrl())
                .qrCodeData(entity.getQrCodeData())
                .createdAt(entity.getCreatedAt())
                .processedAt(entity.getUpdatedAt())
                .build();
    }
} 