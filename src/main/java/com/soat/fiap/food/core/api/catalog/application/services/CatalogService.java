package com.soat.fiap.food.core.api.catalog.application.services;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.request.CatalogRequestMapper;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.CatalogResponseMapper;
import com.soat.fiap.food.core.api.catalog.application.ports.in.CatalogUseCase;
import com.soat.fiap.food.core.api.catalog.application.ports.out.CatalogRepository;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogException;
import com.soat.fiap.food.core.api.shared.exception.ResourceNotFoundException;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
* Serviço que implementa o caso de uso de Catálogo.
 */
@Service
public class CatalogService implements CatalogUseCase {

    private final CatalogRepository catalogRepository;
    private final CatalogRequestMapper catalogRequestMapper;
    private final CatalogResponseMapper catalogResponseMapper;
    private final CustomLogger logger;

    public CatalogService(
            CatalogRepository catalogRepository,
            CatalogRequestMapper catalogRequestMapper,
            CatalogResponseMapper catalogResponseMapper
    ) {
        this.catalogRepository = catalogRepository;
        this.catalogRequestMapper = catalogRequestMapper;
        this.catalogResponseMapper = catalogResponseMapper;
        this.logger = CustomLogger.getLogger(getClass());
    }

    /**
     * Salva um catálogo.
     *
     * @param catalogRequest Catálogo a ser salvo
     * @return Catálogo salvo com identificadores atualizados
     */
    @Override
    @Transactional
    public CatalogResponse saveCatalog(CatalogRequest catalogRequest) {

        var catalog = catalogRequestMapper.toDomain(catalogRequest);
        logger.debug("Criando catalogo: {}", catalog.getName());

        if (catalogRepository.existsByName(catalog.getName())) {
            logger.warn("Tentativa de cadastrar catalogo com nome repetido. Nome: {}", catalog.getName());
            throw new CatalogException(String.format("Já existe um catalogo com o nome: %s", catalog.getName()));
        }

        var savedCatalog = catalogRepository.save(catalog);

        logger.debug("Catalogo criado com sucesso: {}", catalog.getId());

        return catalogResponseMapper.toResponse(savedCatalog);
    }

    /**
     * Atualiza um catálogo.
     *
     * @param id Identificador do catálogo
     * @param catalogRequest Catálogo a ser atualizado
     * @return Catálogo salvo com identificadores atualizados
     */
    @Override
    @Transactional
    public CatalogResponse updateCatalog(Long id, CatalogRequest catalogRequest) {

        var newCatalog = catalogRequestMapper.toDomain(catalogRequest);

        logger.debug("Atualizando catalogo: {}", id);

        if (!catalogRepository.existsById(id)) {
            logger.warn("Tentativa de atualizar catalogo inexistente. Id: {}", newCatalog.getId());
            throw new ResourceNotFoundException("Catalogo", "id", newCatalog.getId());
        }
        else if (catalogRepository.existsByName(newCatalog.getName())) {
            logger.warn("Tentativa de cadastrar catalogo com nome repetido. Nome: {}", newCatalog.getName());
            throw new CatalogException(String.format("Já existe um catalogo com o nome: %s", newCatalog.getName()));
        }

        newCatalog.setId(id);
        newCatalog.markUpdatedNow();
        var updatedCatalog = catalogRepository.save(newCatalog);

        logger.debug("Catalogo atualizado com sucesso: {}", newCatalog.getId());

        return catalogResponseMapper.toResponse(updatedCatalog);
    }

    /**
     * Busca um catálogo pelo seu ID.

     * @param id Identificador do catálogo
     * @return o catálogo
     */
    @Override
    @Transactional(readOnly = true)
    public CatalogResponse getCatalogById(Long id) {
        logger.debug("Buscando catalogo de id: {}", id);

        var existingCatalog = catalogRepository.findById(id);

        if (existingCatalog.isEmpty()) {
            logger.warn("Catalogo não encontrado. Id: {}", id);
            throw new ResourceNotFoundException("Catalogo", id);
        }

        return catalogResponseMapper.toResponse(existingCatalog.get());
    }

    /**
     * Lista todos os catálogos.
     *
     * @return Lista contendo todos os catálogos
     */
    @Override
    @Transactional(readOnly = true)
    public List<CatalogResponse> getAllCatalogs() {
        logger.debug("Buscando todos os catálogos");
        var existingCatalogs = catalogRepository.findAll();
        logger.debug("Encontradas {} catalogos", existingCatalogs.size());

        return catalogResponseMapper.toResponseList(existingCatalogs);
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

        if (!catalogRepository.existsById(id)) {
            logger.warn("Tentativa de excluir catalogo inexistente. Id: {}", id);
            throw new ResourceNotFoundException("Catalogo", id);
        }

        catalogRepository.delete(id);

        logger.debug("Catalogo excluido com sucesso: {}", id);
    }
}