package com.soat.fiap.food.core.api.catalog.application.usecases.catalog.impl;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;
import com.soat.fiap.food.core.api.catalog.application.mapper.response.CatalogResponseMapper;
import com.soat.fiap.food.core.api.catalog.application.usecases.catalog.UpdateCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogConflictException;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.transaction.annotation.Transactional;

/**
 * Caso de uso (implementação concreta): Atualizar catálogo.
 *
 */
@Slf4j
public class UpdateCatalogUseCaseImpl implements UpdateCatalogUseCase {

    private final CatalogResponseMapper catalogResponseMapper;
    private final CatalogGateway catalogGateway;

    public UpdateCatalogUseCaseImpl(
            CatalogResponseMapper catalogResponseMapper,
            CatalogGateway catalogRepository
    ) {
        this.catalogResponseMapper = catalogResponseMapper;
        this.catalogGateway = catalogRepository;
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
