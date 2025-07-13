package com.soat.fiap.food.core.api.payment.core.interfaceadapters.dto.mappers;

import com.soat.fiap.food.core.api.payment.core.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.core.interfaceadapters.dto.PaymentDTO;

public class PaymentDTOMapper {

	/**
	 * Cria uma instância de {@link Payment} a partir de um {@link PaymentDTO}.
	 *
	 * @param dto
	 *            DTO de pagamento contendo os dados a serem convertidos
	 * @return Instância da entidade de domínio {@link Payment}
	 */
	public static Payment toDomain(PaymentDTO dto) {
		return Payment.fromDTO(dto);
	}

	/**
	 * Cria uma instância de {@link PaymentDTO} a partir de um {@link Payment}.
	 *
	 * @param payment
	 *            Entidade de domínio de pagamento contendo os dados a serem
	 *            convertidos
	 * @return Instância do DTO de pagamento {@link PaymentDTO}
	 */
	public static PaymentDTO toDTO(Payment payment) {
		return new PaymentDTO(payment.getId(), payment.getUserId(), payment.getOrderId(), payment.getType(),
				payment.getExpiresIn(), payment.getTid(), payment.getAmount(),
				payment.getQrCode() != null ? payment.getQrCode().value() : null, payment.getStatus(),
				payment.getPaidAt(), payment.getObservations(), payment.getAuditInfo().getCreatedAt(),
				payment.getAuditInfo().getUpdatedAt());
	}

}
