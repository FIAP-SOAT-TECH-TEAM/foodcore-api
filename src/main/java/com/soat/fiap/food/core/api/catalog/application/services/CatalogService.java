package com.soat.fiap.food.core.api.catalog.application.services;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.request.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CategoryResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.request.CatalogRequestMapper;
import com.soat.fiap.food.core.api.catalog.application.mapper.request.CategoryRequestMapper;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.CatalogResponseMapper;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.CategoryResponseMapper;
import com.soat.fiap.food.core.api.catalog.application.ports.in.CatalogUseCase;
import com.soat.fiap.food.core.api.catalog.application.ports.out.CatalogRepository;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogConflictException;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.shared.exception.ResourceNotFoundException;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import org.springframework.beans.BeanUtils;
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
    private final CategoryRequestMapper categoryRequestMapper;

    private final CatalogResponseMapper catalogResponseMapper;
    private final CategoryResponseMapper categoryResponseMapper;

    private final CustomLogger logger;

    public CatalogService(
            CatalogRepository catalogRepository,
            CatalogRequestMapper catalogRequestMapper,
            CatalogResponseMapper catalogResponseMapper,
            CategoryRequestMapper categoryRequestMapper,
            CategoryResponseMapper categoryResponseMapper
    ) {
        this.catalogRepository = catalogRepository;
        this.catalogRequestMapper = catalogRequestMapper;
        this.catalogResponseMapper = catalogResponseMapper;
        this.categoryRequestMapper = categoryRequestMapper;
        this.categoryResponseMapper = categoryResponseMapper;
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
            throw new CatalogConflictException("Catalogo", "Nome", catalog.getName());
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

        var existingCatalog = catalogRepository.findById(id);

        logger.debug("Atualizando catalogo: {}", id);

        if (existingCatalog.isEmpty()) {
            logger.warn("Tentativa de atualizar catalogo inexistente. Id: {}", id);
            throw new CatalogNotFoundException("Catalogo", id);
        }
        else if (catalogRepository.existsByNameAndIdNot(catalogRequest.getName(), id)) {
            logger.warn("Tentativa de cadastrar catalogo com nome repetido. Nome: {}", catalogRequest.getName());
            throw new CatalogConflictException("Catalogo", "Nome", catalogRequest.getName());
        }

        BeanUtils.copyProperties(catalogRequest, existingCatalog.get());

        existingCatalog.get().markUpdatedNow();
        var updatedCatalog = catalogRepository.save(existingCatalog.get());

        logger.debug("Catalogo atualizado com sucesso: {}", id);

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
        if (catalogRepository.existsCategoryByCatalogId(id)) {
            logger.warn("Tentativa de excluir catalogo com categorias associadas. Id: {}", id);
            throw new CatalogConflictException("Não é possível excluir este catalogo porque existem categorias associadas a ele");
        }

        catalogRepository.delete(id);

        logger.debug("Catalogo excluido com sucesso: {}", id);
    }

    /**
     * Salva uma categoria.
     *
     * @param categoryRequest Categoria a ser salva
     * @return Categoria salva com identificadores atualizados
     */
    @Override
    @Transactional
    public CategoryResponse saveCategory(CategoryRequest categoryRequest) {

        var category = categoryRequestMapper.toDomain(categoryRequest);
        var catalog = catalogRepository.findById(categoryRequest.getCatalogId());

        logger.debug("Criando categoria: {}", categoryRequest.getName());

        if (catalog.isEmpty()) {
            logger.warn("Tentativa de cadastrar categoria com catalogo inexistente. ID catalogo: {}", categoryRequest.getCatalogId());
            throw new ResourceNotFoundException("Catalogo", categoryRequest.getCatalogId());
        }

        catalog.get().addCategory(category);

        var savedCatalog = catalogRepository.save(catalog.get());
        var savedCategory = savedCatalog.getCategories().getLast();

        var savedCategoryToResponse = categoryResponseMapper.toResponse(savedCategory);

        logger.debug("Categoria criada com sucesso: {}", savedCategoryToResponse.getId());

        return savedCategoryToResponse;
    }

    /**
     * Atualiza uma categoria.
     *
     * @param categoryRequest Categoria a ser atualizada
     * @return Categoria atualizada com identificadores atualizados
     */
    @Override
    @Transactional
    public CategoryResponse updateCategory(Long catalogId, Long categoryId, CategoryRequest categoryRequest) {

        var category = categoryRequestMapper.toDomain(categoryRequest);
        var existingCatalog = catalogRepository.findById(catalogId);

        logger.debug("Atualizando categoria: {}", categoryId);

        if (existingCatalog.isEmpty()) {
            logger.warn("Tentativa de atualizar categoria com catálogo inexistente. Id: {}", catalogId);
            throw new ResourceNotFoundException("Catalogo", catalogId);
        }

        category.setId(categoryId);
        existingCatalog.get().updateCategory(category);

        var updatedCatalog = catalogRepository.save(existingCatalog.get());
        var updatedCategory = updatedCatalog.getCategoryById(categoryId);

        var updatedCategoryToResponse = categoryResponseMapper.toResponse(updatedCategory);

        logger.debug("Categoria atualizada com sucesso: {}", catalogId);

        return updatedCategoryToResponse;
    }
}