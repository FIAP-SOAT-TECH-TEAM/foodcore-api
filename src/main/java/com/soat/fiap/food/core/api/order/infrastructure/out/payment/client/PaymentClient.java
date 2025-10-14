package com.soat.fiap.food.core.api.order.infrastructure.out.payment.client;

import com.soat.fiap.food.core.api.order.infrastructure.out.payment.entity.PaymentStatusEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;


/**
 * Cliente de conexão com a API do microsserviço de Pagamento
 */
@FeignClient(name = "${payment.service.name}", url = "${payment.service.url}")
public interface PaymentClient {

	@GetMapping("/payments/{orderId}/status")
	ResponseEntity<PaymentStatusEntity> getOrderStatus(@PathVariable Long orderId);
}
