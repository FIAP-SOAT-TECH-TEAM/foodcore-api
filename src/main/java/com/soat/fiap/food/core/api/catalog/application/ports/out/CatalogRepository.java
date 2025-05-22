package com.soat.fiap.food.core.api.catalog.application.ports.out;

import com.soat.fiap.food.core.api.catalog.domain.model.Catalog;

import java.util.List;
import java.util.Optional;

/**
 * Porta de saída para persistência do agregado Catálogo.
 */
public interface CatalogRepository {

    /**
     * Salva o agregado Catálogo.
     *
     * @param catalog Agregado Catálogo a ser salvo
     * @return Agregado salvo com identificadores atualizados
     */
    Catalog save(Catalog catalog);

    /**
     * Busca um catálogo por ID.
     *
     * @param id ID do catálogo
     * @return Optional contendo o catálogo ou vazio se não encontrado
     */
    Optional<Catalog> findById(Long id);

    /**
     * Busca um catálogo por nome.
     *
     * @param name Nome do catalogo
     * @return Optional contendo o catálogo ou vazio se não encontrado
     */
    Optional<Catalog> findByName(String name);

    /**
     * Lista todos os catálogos.
     *
     * @return Lista de catálogos
     */
    List<Catalog> findAll();

    /**
     * Remove um catálogo.
     *
     * @param id ID do catálogo a ser removido
     */
    void delete(Long id);
}