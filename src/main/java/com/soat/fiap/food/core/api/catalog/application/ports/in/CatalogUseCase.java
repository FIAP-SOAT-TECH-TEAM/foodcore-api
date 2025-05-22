package com.soat.fiap.food.core.api.catalog.application.ports.in;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogCreateRequest;
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
     * @param catalog Catálogo a ser salvo
     * @return Catálogo salvo com possíveis atualizações de identificadores
     */
    Catalog saveCatalog(CatalogCreateRequest catalog);

    /**
     * Busca um catálogo pelo seu ID.
     *
     * @param id Identificador do catálogo
     * @return Optional contendo o catálogo caso encontrado, ou vazio se não encontrado
     */
    Optional<Catalog> getCatalogById(Long id);

    /**
     * Lista todos os catálogos disponíveis.
     *
     * @return Lista contendo todos os catálogos
     */
    List<Catalog> getAllCatalogs();

    /**
     * Remove um catálogo pelo seu ID.
     *
     * @param id Identificador do catálogo a ser removido
     */
    void deleteCatalog(Long id);
}