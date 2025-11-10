package unit.fixtures;

import java.math.BigDecimal;
import java.util.List;

import com.soat.fiap.food.core.order.core.interfaceadapters.dto.product.ProductDTO;
import com.soat.fiap.food.core.order.core.interfaceadapters.dto.product.StockDTO;

/**
 * Fixture para criação de catálogos de produtos simulados para testes
 * unitários.
 */
public class CatalogFixture {

	/**
	 * Cria um catálogo contendo um único produto ativo e com categoria ativa.
	 *
	 * @return Lista com um único {@link ProductDTO} válido.
	 */
	public static List<ProductDTO> createCatalogWithProducts() {
		return List.of(new ProductDTO(1L, "Hambúrguer", BigDecimal.valueOf(25.00), true, true, new StockDTO(10)));
	}

	/**
	 * Cria um catálogo contendo múltiplos produtos ativos com estoque suficiente.
	 *
	 * @return Lista com múltiplos {@link ProductDTO} válidos.
	 */
	public static List<ProductDTO> createCatalogWithMultipleProducts() {
		return List.of(new ProductDTO(1L, "Hambúrguer", BigDecimal.valueOf(25.00), true, true, new StockDTO(10)),
				new ProductDTO(2L, "Refrigerante", BigDecimal.valueOf(8.00), true, true, new StockDTO(20)));
	}
}
