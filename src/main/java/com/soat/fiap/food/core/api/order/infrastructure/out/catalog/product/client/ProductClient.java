package com.soat.fiap.food.core.api.order.infrastructure.out.catalog.product.client;

import com.soat.fiap.food.core.api.order.infrastructure.out.catalog.product.entity.ProductEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.http.ResponseEntity;
import java.util.List;

/**
 * Cliente de conexão com a API do microsserviço de Catalog (Product)
 */
@FeignClient(
		name = "${catalog.service.name}",
		url = "${catalog.service.url}"
)
public interface ProductClient {

	@GetMapping("/categories/products")
	ResponseEntity<List<ProductEntity>> getProductsByIds(@RequestParam List<Long> productIds);
}
