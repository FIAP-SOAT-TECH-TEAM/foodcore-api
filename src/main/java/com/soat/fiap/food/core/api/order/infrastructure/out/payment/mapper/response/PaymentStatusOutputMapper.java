package com.soat.fiap.food.core.api.order.infrastructure.out.payment.mapper.response;

import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import com.soat.fiap.food.core.api.order.core.application.outputs.payment.PaymentStatusOutput;
import com.soat.fiap.food.core.api.order.infrastructure.out.payment.entity.PaymentStatusEntity;

/**
 * Mapper responsável por converter {@link PaymentStatusEntity} em {@link PaymentStatusOutput}.
 * <p>
 * Esse mapper é utilizado para extrair apenas os dados relevantes da entidade de pagamento
 * que serão utilizados na lógica de aplicação (caso de uso).
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface PaymentStatusOutputMapper {

	/**
	 * Converte uma entidade de status de pagamento em um output contendo apenas os dados
	 * necessários para a lógica de aplicação.
	 *
	 * @param entity
	 *            entidade de status de pagamento retornada pela API do microsserviço
	 * @return objeto de saída da camada de aplicação com os dados mapeados
	 */
	PaymentStatusOutput toOutput(PaymentStatusEntity entity);
}
