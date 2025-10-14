package com.soat.fiap.food.core.api.order.infrastructure.common.source;

import com.soat.fiap.food.core.api.order.core.application.outputs.catalog.product.ProductOutput;

import java.util.List;

/**
 * DataSource para obtenção de Produto.
 */
public interface ProductDataSource {

	/**
	 * Retorna uma lista de Produtos por IDs
	 *
	 * @param productIds
	 *            IDs do produtos
	 */
	List<ProductOutput> findByProductIds(List<Long> productIds);

}
