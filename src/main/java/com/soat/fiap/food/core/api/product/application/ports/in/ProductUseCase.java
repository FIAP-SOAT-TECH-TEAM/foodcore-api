package com.soat.fiap.food.core.api.product.application.ports.in;

import com.soat.fiap.food.core.api.product.domain.model.Product;

import java.util.List;
import java.util.Optional;

/**
 * Porta de entrada para operações relacionadas a produtos.
 */
public interface ProductUseCase {
    /**
     * Cadastra um novo produto
     * @param product Produto a ser cadastrado
     * @return Produto cadastrado com ID gerado
     */
    Product createProduct(Product product);
    
    /**
     * Atualiza um produto existente
     * @param id ID do produto a ser atualizado
     * @param product Produto com dados atualizados
     * @return Produto atualizado
     */
    Product updateProduct(Long id, Product product);
    
    /**
     * Busca um produto por ID
     * @param id ID do produto
     * @return Optional contendo o produto ou vazio se não encontrado
     */
    Optional<Product> getProductById(Long id);
    
    /**
     * Lista todos os produtos
     * @return Lista de produtos
     */
    List<Product> getAllProducts();
    
    /**
     * Lista produtos por categoria
     * @param categoryId ID da categoria
     * @return Lista de produtos da categoria
     */
    List<Product> getProductsByCategory(Long categoryId);
    
    /**
     * Remove um produto
     * @param id ID do produto a ser removido
     */
    void deleteProduct(Long id);
} 