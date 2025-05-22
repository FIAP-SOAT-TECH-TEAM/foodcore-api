package com.soat.fiap.food.core.api.catalog.application.ports.in;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;

import java.util.List;

/**
 * Porta de entrada para operações do caso de uso relacionado ao agregado Catálogo.
 */
public interface CatalogUseCase {

    /**
     * Salva um catálogo.
     *
     * @param catalogRequest Catálogo a ser salvo
     * @return Catálogo salvo com possíveis atualizações de identificadores
     */
    CatalogResponse saveCatalog(CatalogRequest catalogRequest);

    /**
     * Atualiza um catálogo.
     *
     * @param id ID do Catálogo a ser atualizado
     * @param catalogRequest Catálogo a ser atualizado
     * @return Catálogo atualizado com possíveis atualizações de identificadores
     */
    CatalogResponse updateCatalog(Long id, CatalogRequest catalogRequest);

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