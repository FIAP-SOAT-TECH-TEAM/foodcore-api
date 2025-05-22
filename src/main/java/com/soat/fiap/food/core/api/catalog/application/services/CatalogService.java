package com.soat.fiap.food.core.api.catalog.application.services;

import com.soat.fiap.food.core.api.catalog.application.ports.in.CatalogUseCase;
import com.soat.fiap.food.core.api.catalog.application.ports.out.CatalogRepository;
import com.soat.fiap.food.core.api.catalog.domain.model.Catalog;
import com.soat.fiap.food.core.api.shared.exception.ResourceConflictException;
import com.soat.fiap.food.core.api.shared.exception.ResourceNotFoundException;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
* Serviço que implementa o caso de uso de Catálogo.
 */
@Service
public class CatalogService implements CatalogUseCase {

    private final CatalogRepository catalogRepository;
    private final CustomLogger logger;

    public CatalogService(
            CatalogRepository catalogRepository

    ) {
        this.catalogRepository = catalogRepository;
        this.logger = CustomLogger.getLogger(getClass());
    }

    /**
     * Salva um catálogo.
     *
     * @param catalog Catálogo a ser salvo
     * @return Catálogo salvo com identificadores atualizados
     */
    @Override
    @Transactional
    public Catalog saveCatalog(Catalog catalog) {
        logger.debug("Criando catalogo: {}", catalog.getName());

        var existingCatalog = catalogRepository.findByName(catalog.getName());

        if (existingCatalog.isPresent()) {
            logger.warn("Tentativa de cadastrar catalogo com nome repetido. Nome: {}", catalog.getName());
            throw new ResourceConflictException("Catalogo", "nome", catalog.getName());
        }

        var savedCatalog = catalogRepository.save(catalog);

        logger.debug("Catalogo criado com sucesso: {}", catalog.getId());

        return savedCatalog;
    }

    /**
     * Busca um catálogo pelo seu ID.

     * @param id Identificador do catálogo
     * @return Optional contendo o catálogo, ou vazio se não encontrado
     */
    @Override
    @Transactional(readOnly = true)
    public Optional<Catalog> getCatalogById(Long id) {
        logger.debug("Buscando catalogo de id: {}", id);

        var existingCatalog = catalogRepository.findById(id);

        if (existingCatalog.isEmpty()) {
            logger.warn("Catalogo não encontrado. Id: {}", id);
            throw new ResourceNotFoundException("Catalogo", id);
        }

        return catalogRepository.findById(id);
    }

    /**
     * Lista todos os catálogos.
     *
     * @return Lista contendo todos os catálogos
     */
    @Override
    @Transactional(readOnly = true)
    public List<Catalog> getAllCatalogs() {
        logger.debug("Buscando todos os catálogos");
        var existingCatalogs = catalogRepository.findAll();
        logger.debug("Encontradas {} catalogos", existingCatalogs.size());

        return existingCatalogs;
    }

    /**
     * Remove um catálogo pelo seu ID.
     *
     * @param id Identificador do catálogo a ser removido
     */
    @Override
    @Transactional
    public void deleteCatalog(Long id) {
        logger.debug("Excluindo catalogo de id: {}", id);

        var existingCatalog = catalogRepository.findById(id);

        if (existingCatalog.isEmpty()) {
            logger.warn("Tentativa de excluir catalogo inexistente. Id: {}", id);
            throw new ResourceNotFoundException("Catalogo", id);
        }

        catalogRepository.delete(id);

        logger.debug("Catalogo excluido com sucesso: {}", id);
    }
}