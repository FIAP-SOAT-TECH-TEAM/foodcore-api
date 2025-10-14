package com.soat.fiap.food.core.api.order.infrastructure.common.source;

import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.dto.ProductDTO;

import java.util.List;
import java.util.Optional;

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
	Optional<List<ProductDTO>> findByProductIds(List<Long> productIds);

}
