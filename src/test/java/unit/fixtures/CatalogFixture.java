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

	/**
	 * Cria um produto customizado para cenários de teste específicos. Permite
	 * controlar todos os atributos relevantes, como nome, preço, estoque e flags de
	 * ativação.
	 *
	 * @param id
	 *            Identificador único do produto.
	 * @param name
	 *            Nome do produto.
	 * @param price
	 *            Preço do produto em {@link BigDecimal}.
	 * @param active
	 *            Indica se o produto está ativo no catálogo.
	 * @param categoryActive
	 *            Indica se a categoria do produto está ativa.
	 * @param stockQty
	 *            Quantidade disponível em estoque.
	 *
	 * @return Instância configurada de {@link ProductDTO}.
	 */
	public static ProductDTO createCatalogProduct(Long id, String name, BigDecimal price, boolean active,
			boolean categoryActive, int stockQty) {
		return new ProductDTO(id, name, price, active, categoryActive, new StockDTO(stockQty));
	}

}
