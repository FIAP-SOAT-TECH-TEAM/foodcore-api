package com.soat.fiap.food.core.api.payment.core.interfaceadapters.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.api.payment.core.domain.vo.PaymentStatus;

public record PaymentDTO(Long id, Long userId, Long orderId, PaymentMethod type, LocalDateTime expiresIn, String tid,
		BigDecimal amount, String qrCode, PaymentStatus status, LocalDateTime paidAt, String observations,
		LocalDateTime createdAt, LocalDateTime updatedAt) {
}
