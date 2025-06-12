package com.soat.fiap.food.core.api.catalog.core.application.usecases.catalog;

import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.requests.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.responses.CatalogResponse;
import com.soat.fiap.food.core.api.catalog.core.application.mapper.response.CatalogResponseMapper;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogConflictException;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

/**
 * Caso de uso: Atualizar catálogo.
 *
 */
@Slf4j
public class UpdateCatalogUseCase {

    private final CatalogResponseMapper catalogResponseMapper;
    private final CatalogGateway catalogGateway;

    public UpdateCatalogUseCase(
            CatalogResponseMapper catalogResponseMapper,
            CatalogGateway catalogGateway
    ) {
        this.catalogResponseMapper = catalogResponseMapper;
        this.catalogGateway = catalogGateway;
    }

    /**
     * Atualiza um catálogo.
     *
     * @param id Identificador do catálogo
     * @param catalogRequest Catálogo a ser atualizado
     * @return Catálogo salvo com identificadores atualizados
     */
    public CatalogResponse updateCatalog(Long id, CatalogRequest catalogRequest) {

        var existingCatalog = catalogGateway.findById(id);

        log.debug("Atualizando catalogo: {}", id);

        if (existingCatalog.isEmpty()) {
            log.warn("Tentativa de atualizar catalogo inexistente. Id: {}", id);
            throw new CatalogNotFoundException("Catalogo", id);
        }
        else if (catalogGateway.existsByNameAndIdNot(catalogRequest.getName(), id)) {
            log.warn("Tentativa de cadastrar catalogo com nome repetido. Nome: {}", catalogRequest.getName());
            throw new CatalogConflictException("Catalogo", "Nome", catalogRequest.getName());
        }

        BeanUtils.copyProperties(catalogRequest, existingCatalog.get());

        existingCatalog.get().markUpdatedNow();
        var updatedCatalog = catalogGateway.save(existingCatalog.get());

        log.debug("Catalogo atualizado com sucesso: {}", id);

        return catalogResponseMapper.toResponse(updatedCatalog);
    }
}
