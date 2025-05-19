package com.soat.fiap.food.core.api.catalog.application.ports.out;

import com.soat.fiap.food.core.api.catalog.domain.model.Product;
import java.util.List;
import java.util.Optional;

/**
 * Porta de saída para persistência de produtos
 */
public interface ProductRepository {
    
    /**
     * Salva um produto
     * @param product Produto a ser salvo
     * @return Produto salvo com ID gerado
     */
    Product save(Product product);
    
    /**
     * Busca um produto por ID
     * @param id ID do produto
     * @return Optional contendo o produto ou vazio se não encontrado
     */
    Optional<Product> findById(Long id);
    
    /**
     * Lista todos os produtos
     * @return Lista de produtos
     */
    List<Product> findAll();
    
    /**
     * Lista produtos por categoria
     * @param categoryId ID da categoria
     * @return Lista de produtos da categoria
     */
    List<Product> findByCategory(Long categoryId);
    
    /**
     * Lista apenas produtos ativos
     * @return Lista de produtos ativos
     */
    List<Product> findAllActive();
    
    /**
     * Remove um produto
     * @param id ID do produto a ser removido
     */
    void delete(Long id);
} 