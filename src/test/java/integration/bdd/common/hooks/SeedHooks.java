package integration.bdd.common.hooks;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.soat.fiap.food.core.order.infrastructure.out.persistence.postgres.repository.SpringDataOrderRepository;

import integration.bdd.common.config.CucumberSpringConfiguration;
import io.cucumber.java.After;

/**
 * Classe responsável por definir Hooks comuns relacionados a Seed de banco de
 * dados
 */
public class SeedHooks extends CucumberSpringConfiguration {

	private static final Logger log = LoggerFactory.getLogger(SeedHooks.class);

	@Autowired
	protected SpringDataOrderRepository springDataOrderRepository;

	/**
	 * Hook executado antes de cada cenário Cucumber. Remove todos os documentos da
	 * coleção para garantir um estado limpo de banco.
	 */
	@After
	public void limparBanco() {
		log.debug("Resetando banco de dados");
		springDataOrderRepository.deleteAll();
	}
}
