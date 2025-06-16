package com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.core.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Caso de uso: Obter todos catálogo.
 *
 */
@Slf4j
public class GetAllCatalogsUseCase {

    /**
     * Obtém todos os catálogos.
     *
     * @return Lista contendo todos os catálogos
     */
    public static List<Catalog> getAllCatalogs(CatalogGateway gateway) {
        var existingCatalogs = gateway.findAll();
        log.debug("Encontradas {} catalogos", existingCatalogs.size());

        return existingCatalogs;
    }
}
