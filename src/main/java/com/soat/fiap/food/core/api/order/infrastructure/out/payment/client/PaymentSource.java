package com.soat.fiap.food.core.api.order.infrastructure.out.payment.client;

import com.soat.fiap.food.core.api.order.core.application.outputs.payment.PaymentStatusOutput;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.order.infrastructure.out.payment.exceptions.PaymentException;
import com.soat.fiap.food.core.api.order.infrastructure.out.payment.mapper.response.PaymentStatusOutputMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Implementação concreta: DataSource para comunicação com o microsserviço de
 * Pagamento
 */
@Component @Slf4j @RequiredArgsConstructor
public class PaymentSource implements PaymentDataSource {

	@Autowired
	private PaymentClient client;

	@Autowired
	private PaymentStatusOutputMapper paymentStatusOutputMapper;

	@Override
	public PaymentStatusOutput getOrderStatus(Long orderId) {
		try {
			var response = client.getOrderStatus(orderId);

			if (response.getStatusCode().is2xxSuccessful() && response.getBody() != null) {
				var paymentStatusEntity = response.getBody();
				return paymentStatusOutputMapper.toOutput(paymentStatusEntity);
			} else {
				String errorMsg = "Erro do microsserviço de pagamento | Status code: "
						+ response.getStatusCode().value();
				log.warn(errorMsg);
				throw new PaymentException(errorMsg, null, response.getStatusCode().value());
			}
		} catch (PaymentException e) {
			throw e;
		} catch (Exception e) {
			log.error("Erro inesperado ao contatar a API do microsserviço de pagamento", e);
			throw new PaymentException("Erro inesperado ao chamar API do microsserviço de pagamento", e, 500);
		}
	}
}
