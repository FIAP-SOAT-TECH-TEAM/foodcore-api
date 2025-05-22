package com.soat.fiap.food.core.api.catalog.application.ports.in;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogCreateRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogUpdateRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;
import com.soat.fiap.food.core.api.catalog.domain.model.Catalog;

import java.util.List;
import java.util.Optional;

/**
 * Porta de entrada para operações do caso de uso relacionado ao agregado Catálogo.
 */
public interface CatalogUseCase {

    /**
     * Salva um catálogo.
     *
     * @param catalogCreateRequest Catálogo a ser salvo
     * @return Catálogo salvo com possíveis atualizações de identificadores
     */
    CatalogResponse saveCatalog(CatalogCreateRequest catalogCreateRequest);

    /**
     * Atualiza um catálogo.
     *
     * @param catalogUpdateRequest Catálogo a ser atualizado
     * @return Catálogo atualizado com possíveis atualizações de identificadores
     */
    CatalogResponse updateCatalog(CatalogUpdateRequest catalogUpdateRequest);

    /**
     * Busca um catálogo pelo seu ID.
     *
     * @param id Identificador do catálogo
     * @return catálogo caso encontrado
     */
    CatalogResponse getCatalogById(Long id);

    /**
     * Lista todos os catálogos disponíveis.
     *
     * @return Lista contendo todos os catálogos
     */
    List<CatalogResponse> getAllCatalogs();

    /**
     * Remove um catálogo pelo seu ID.
     *
     * @param id Identificador do catálogo a ser removido
     */
    void deleteCatalog(Long id);
}