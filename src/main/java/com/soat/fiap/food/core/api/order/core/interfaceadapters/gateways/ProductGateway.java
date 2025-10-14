package com.soat.fiap.food.core.api.order.core.interfaceadapters.gateways;

import com.soat.fiap.food.core.api.order.core.application.outputs.catalog.product.ProductOutput;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.ProductDataSource;

import java.util.List;

/**
 * Gateway para comunicação com o microsserviço de catálogo (Product)
 */
public class ProductGateway {

	private final ProductDataSource productDataSource;

	public ProductGateway(ProductDataSource productDataSource) {
		this.productDataSource = productDataSource;
	}

	/**
	 * Retorna uma lista de Produtos por IDs
	 *
	 * @param productIds
	 *            IDs do produtos
	 */
	public List<ProductOutput> findByProductIds(List<Long> productIds) {
		return productDataSource.findByProductIds(productIds);
	}

}
