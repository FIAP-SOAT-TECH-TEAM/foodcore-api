package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.repository;

import com.soat.fiap.food.core.api.catalog.application.ports.out.CatalogRepository;
import com.soat.fiap.food.core.api.catalog.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.entity.CatalogEntity;
import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.mapper.CatalogEntityMapper;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Adaptador que implementa a porta de saída CatalogRepository
 * responsável por operações sobre o agregado Catálogo.
 */
@Component
public class CatalogRepositoryAdapter implements CatalogRepository {

    private final SpringDataCatalogRepository springDataCatalogRepository;
    private final CatalogEntityMapper catalogEntityMapper;

    public CatalogRepositoryAdapter(
            SpringDataCatalogRepository springDataCatalogRepository,
            CatalogEntityMapper catalogEntityMapper
    ) {
        this.springDataCatalogRepository = springDataCatalogRepository;
        this.catalogEntityMapper = catalogEntityMapper;
    }

    /**
     * Salva o agregado Catálogo.
     *
     * @param catalog Agregado Catálogo a ser salvo
     * @return Agregado salvo com identificadores atualizados
     */
    @Override
    public Catalog save(Catalog catalog) {
        CatalogEntity entity = catalogEntityMapper.toEntity(catalog);
        CatalogEntity saved = springDataCatalogRepository.save(entity);
        return catalogEntityMapper.toDomain(saved);
    }

    /**
     * Busca um catálogo pelo ID.
     *
     * @param id ID do catálogo
     * @return Optional contendo o catálogo ou vazio se não encontrado
     */
    @Override
    public Optional<Catalog> findById(Long id) {
        return springDataCatalogRepository.findById(id)
                .map(catalogEntityMapper::toDomain);
    }

    /**
     * Busca um catálogo pelo nome.
     *
     * @param name nome do catálogo
     * @return Optional contendo o catálogo ou vazio se não encontrado
     */
    @Override
    public Optional<Catalog> findByName(String name) {
        return springDataCatalogRepository.findByName(name)
                .map(catalogEntityMapper::toDomain);
    }

    /**
     * Lista todos os catálogos persistidos.
     *
     * @return Lista de catálogos
     */
    @Override
    public List<Catalog> findAll() {
        return springDataCatalogRepository.findAll().stream()
                .map(catalogEntityMapper::toDomain)
                .collect(Collectors.toList());
    }

    /**
     * Remove um catálogo com base em seu ID.
     *
     * @param id ID do catálogo a ser removido
     */
    @Override
    public void delete(Long id) {
        springDataCatalogRepository.deleteById(id);
    }
}