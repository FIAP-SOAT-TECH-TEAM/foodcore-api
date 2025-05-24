package com.soat.fiap.food.core.api.catalog.application.ports.in;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.request.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.request.ProductRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CategoryResponse;
import com.soat.fiap.food.core.api.catalog.application.dto.response.ProductResponse;

import java.util.List;

/**
 * Porta de entrada para operações do caso de uso relacionado ao agregado Catálogo.
 */
public interface CatalogUseCase {

    /**
     * Salva um catálogo.
     *
     * @param catalogRequest Catálogo a ser salvo
     * @return Catálogo salvo com possíveis atualizações de identificadores
     */
    CatalogResponse saveCatalog(CatalogRequest catalogRequest);

    /**
     * Atualiza um catálogo.
     *
     * @param id ID do Catálogo a ser atualizado
     * @param catalogRequest Catálogo a ser atualizado
     * @return Catálogo atualizado com possíveis atualizações de identificadores
     */
    CatalogResponse updateCatalog(Long id, CatalogRequest catalogRequest);

    /**
     * Busca um catálogo pelo seu ID.
     *
     * @param id Identificador do catálogo
     * @return catálogo caso encontrado
     */
    CatalogResponse getCatalogById(Long id);

    /**
     * Lista todos os catálogos disponíveis.
     *
     * @return Lista contendo todos os catálogos
     */
    List<CatalogResponse> getAllCatalogs();

    /**
     * Remove um catálogo pelo seu ID.
     *
     * @param id Identificador do catálogo a ser removido
     */
    void deleteCatalog(Long id);

    /**
     * Salva uma categoria.
     *
     * @param categoryRequest Categoria a ser salva
     * @return Categoria salva com possíveis atualizações de identificadores
     */
    CategoryResponse saveCategory(CategoryRequest categoryRequest);

    /**
     * Atualiza uma categoria.
     *
     * @param categoryRequest Categoria a ser atualizada
     * @return Categoria atualizada com possíveis atualizações de identificadores
     */
    CategoryResponse updateCategory(Long catalogId, Long categoryId, CategoryRequest categoryRequest);

    /**
     * Busca uma categoria dentro de um catálogo pelo seu ID.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria
     * @return Categoria encontrada
     */
    CategoryResponse getCategoryById(Long catalogId, Long categoryId);

    /**
     * Lista todas as categorias de um catálogo.
     *
     * @param catalogId ID do catálogo
     * @return Lista de categorias do catálogo
     */
    List<CategoryResponse> getAllCategories(Long catalogId);

    /**
     * Remove uma categoria de um catálogo.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria
     */
    void deleteCategory(Long catalogId, Long categoryId);

    /**
     * Salva um produto.
     *
     * @param catalogId ID do catálogo
     * @param productRequest Produto a ser salvo
     * @return Produto salvo com identificadores atualizados
     */
    ProductResponse saveProduct(Long catalogId, ProductRequest productRequest);

    /**
     * Atualiza um produto.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria
     * @param productId ID do produto a ser atualizado
     * @param productRequest Produto com os dados atualizados
     * @return Produto atualizado com identificadores atualizados
     */
    ProductResponse updateProduct(Long catalogId, Long categoryId, Long productId, ProductRequest productRequest);

    /**
     * Busca um produto por ID dentro de uma categoria.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria
     * @param productId ID do produto
     * @return Produto encontrado
     */
    ProductResponse getProductById(Long catalogId, Long categoryId, Long productId);

    /**
     * Lista todas os produtos de ua categoria.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria
     * @return Lista de produtos de uma categoria
     */
    List<ProductResponse> getAllProducts(Long catalogId, Long categoryId);

    /**
     * Remove um produto de uma categoria.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria
     * @param productId ID do produto a ser removido
     */
    void deleteProduct(Long catalogId, Long categoryId, Long productId);
}