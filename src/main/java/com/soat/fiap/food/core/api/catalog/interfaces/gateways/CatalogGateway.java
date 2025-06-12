package com.soat.fiap.food.core.api.catalog.interfaces.gateways;

import com.soat.fiap.food.core.api.catalog.domain.model.Catalog;
import com.soat.fiap.food.core.api.catalog.infrastructure.shared.DataSource;

import java.util.List;
import java.util.Optional;

/**
 * Gateway para persistência do agregado Catálogo.
 */
public class CatalogGateway {

    private final DataSource dataSource;

    public CatalogGateway(
            DataSource dataSource
    ) {
        this.dataSource = dataSource;
    }

    /**
     * Salva o agregado Catálogo.
     *
     * @param catalog Agregado Catálogo a ser salvo
     * @return Agregado salvo com identificadores atualizados
     */
    public Catalog save(Catalog catalog) {
        return dataSource.save(catalog);
    }

    /**
     * Busca um catálogo pelo ID.
     *
     * @param id ID do catálogo
     * @return Optional contendo o catálogo ou vazio se não encontrado
     */
    public Optional<Catalog> findById(Long id) {
        return dataSource.findById(id);
    }

    /**
     * Busca um catálogo pelo nome.
     *
     * @param name nome do catálogo
     * @return Optional contendo o catálogo ou vazio se não encontrado
     */
    public Optional<Catalog> findByName(String name) {
        return dataSource.findByName(name);
    }

    /**
     * Lista todos os catálogos persistidos.
     *
     * @return Lista de catálogos
     */
    public List<Catalog> findAll() {
        return dataSource.findAll();
    }

    /**
     * Verifica se existe um catalogo com um determinado ID.
     *
     * @param id ID do catálogo
     * @return true se existir um catálogo com um determinado ID, false caso contrário
     */
    public boolean existsById(Long id) {
        return dataSource.existsById(id);
    }

    /**
     * Verifica se existe um catalogo com um determinado nome.
     *
     * @param name Nome do catálogo
     * @return true se existir um catálogo com um determinado nome, false caso contrário
     */
    public boolean existsByName(String name) {
        return dataSource.existsByName(name);
    }

    /**
     * Verifica se existe outro catálogo com o mesmo nome, mas com ID diferente.
     *
     * @param name Nome do catálogo
     * @param id   ID do catálogo que está sendo atualizado
     * @return true se existir outro catálogo com o mesmo nome e ID diferente, false caso contrário
     */
    public boolean existsByNameAndIdNot(String name, Long id) {
        return dataSource.existsByNameAndIdNot(name, id);
    }

    /**
     * Verifica se existe pelo menos uma categoria associada ao catálogo com o ID informado.
     *
     * @param catalogId ID do catálogo
     * @return true se houver ao menos uma categoria associada, false caso contrário
     */
    public boolean existsCategoryByCatalogId(Long catalogId) {
        return dataSource.existsCategoryByCatalogId(catalogId);
    }

    /**
     * Remove um catálogo com base em seu ID.
     *
     * @param id ID do catálogo a ser removido
     */
    public void delete(Long id) {
        dataSource.delete(id);
    }

    /**
     * Retorna um catalogo pelo ID do produto
     *
     * @param productId ID do produto
     */
    public Optional<Catalog> findByProductId(Long productId) {
        return dataSource.findByProductId(productId);
    }
}