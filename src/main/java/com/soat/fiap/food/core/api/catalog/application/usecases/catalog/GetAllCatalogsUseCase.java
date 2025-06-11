package com.soat.fiap.food.core.api.catalog.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;

import java.util.List;

/**
 * Caso de uso: Obter todos catálogo.
 *
 */
public interface GetAllCatalogsUseCase {

    /**
     * Lista todos os catálogos disponíveis.
     *
     * @return Lista contendo todos os catálogos
     */
    List<CatalogResponse> getAllCatalogs();
}
