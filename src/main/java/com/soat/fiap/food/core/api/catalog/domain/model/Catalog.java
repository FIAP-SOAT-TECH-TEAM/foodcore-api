package com.soat.fiap.food.core.api.catalog.domain.model;

import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogException;
import com.soat.fiap.food.core.api.shared.vo.AuditInfo;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entidade de domínio que representa um catalogo de produto
 * AGGREGATE ROOT:
 *  - Toda modificação de entidades internas do agregado devem passar pela entidade raíz;
 *  - Único ponto de entrada para qualquer entidade interna do agregado (Lei de Demeter);
 *  - Entidades dentro deste agregado podem se referenciar via id ou objeto;
 *  - Entidades de outros agregados só podem referenciar esta entidade raiz, e isto deve ser via Id;
 */
@Data
public class Catalog {
    private Long id;
    private String name;
    private AuditInfo auditInfo = new AuditInfo();

    private List<Category> categories;

    /**
     * Construtor que inicializa o catálogo com um nome.
     *
     * @param name o nome do catálogo
     * @throws CatalogException se o nome for inválido
     */
    public Catalog(String name) {
        validate(name);
        this.name = name;
    }

    /**
     * Valida o nome do catálogo.
     *
     * @param name nome a ser validado
     * @throws NullPointerException se o nome for nulo
     * @throws CatalogException se o nome tiver mais que 100 caracteres
     */
    private void validate(String name) {
        Objects.requireNonNull(name, "O nome do catalogo não pode ser nulo");

        if (name.trim().length() > 100) {
            throw new CatalogException("Nome do catalogo deve ter no máximo 100 caracteres");
        }
    }

    /**
     * Fornece uma lista imutável de categorias
     * @return lista imutável de categorias
     */
    public List<Category> getCategories() {
        return Collections.unmodifiableList(this.categories);
    }

    /**
     * Retorna uma categoria pelo ID.
     *
     * @param categoryId o ID da categoria
     * @return a categoria correspondente
     * @throws CatalogException se a categoria não for encontrada
     */
    private Category getCategoryById(Long categoryId) {
        Objects.requireNonNull(categoryId, "O ID da categoria não pode ser nulo");

        return categories.stream()
                .filter(o -> o.getId().equals(categoryId))
                .findFirst()
                .orElseThrow(() -> new CatalogException("Categoria não encontrada"));
    }

    /**
     * Retorna um produto específico dentro de uma categoria.
     *
     * @param categoryId o ID da categoria
     * @param productId o ID do produto
     * @return o produto correspondente
     * @throws CatalogException se o produto ou categoria não for encontrado
     */
    private Product getProductFromCategoryById(Long categoryId, Long productId) {
        Objects.requireNonNull(categoryId, "O ID da categoria não pode ser nulo");
        Objects.requireNonNull(productId, "O ID do produto não pode ser nulo");

        var category = getCategoryById(categoryId);
        return category.getProductById(productId);
    }

    /**
     * Atualiza o nome do catálogo.
     *
     * @param name o novo nome
     * @throws CatalogException se o nome tiver mais que 100 caracteres
     */
    public void setName(String name) {
        if (name.trim().length() > 100) {
            throw new CatalogException("Nome do catalogo deve ter no máximo 100 caracteres");
        }

        this.name = name;
    }

    /**
     * Adiciona uma nova categoria ao catálogo.
     *
     * @param category a categoria a ser adicionada
     * @throws CatalogException se a categoria for nula ou já existir com o mesmo nome
     */
    public void addCategory(Category category) {
        Objects.requireNonNull(category, "A categoria não pode ser nula");
        categories = (categories == null) ? new ArrayList<>() : categories;

        if (categories.stream().anyMatch(c -> c.getName().equals(category.getName()))) {
            throw new CatalogException(String.format("Já existe uma categoria com o nome: %s, cadastrada neste catalogo", category.getName()));
        }

        categories.add(category);
    }

    /**
     * Atualiza uma categoria existente no catálogo.
     *
     * @param newCategory a nova categoria com os dados atualizados
     * @throws CatalogException se a categoria não for encontrada
     */
    public void updateCategory(Category newCategory) {
        Objects.requireNonNull(newCategory, "A categoria não pode ser nula");
        categories = (categories == null) ? new ArrayList<>() : categories;

        var currentCategory = getCategoryById(newCategory.getId());

        currentCategory.setDetails(newCategory.getDetails());
        currentCategory.setImageUrl(newCategory.getImageUrl());
        currentCategory.setDisplayOrder(newCategory.getDisplayOrder());
        currentCategory.setActive(currentCategory.isActive());
        currentCategory.markUpdatedNow();
    }

    /**
     * Remove uma categoria do catálogo.
     *
     * @param categoryId o ID da categoria a ser removida
     */
    public void removeCategory(Long categoryId) {
        Objects.requireNonNull(categoryId, "O ID da categoria não pode ser nula");

        var category = getCategoryById(categoryId);
        categories.remove(category);
    }

    /**
     * Adiciona um produto a uma categoria específica do catálogo.
     *
     * @param categoryId o ID da categoria
     * @param product o produto a ser adicionado
     */
    public void addProductToCategory(Long categoryId, Product product) {
        Objects.requireNonNull(categoryId, "O ID da categoria não pode ser nulo");
        Objects.requireNonNull(product, "O produto não pode ser nulo");

        var category = getCategoryById(categoryId);
        category.addProduct(product);
    }

    /**
     * Atualiza um produto dentro de uma categoria.
     *
     * @param currentCategoryId o ID da categoria atual
     * @param newCategoryId o novo ID da categoria (caso vá mudar de categoria)
     * @param newProduct os novos dados do produto
     */
    public void updateProductInCategory(Long currentCategoryId, Long newCategoryId, Product newProduct) {
        Objects.requireNonNull(currentCategoryId, "O ID da categoria não pode ser nulo");
        Objects.requireNonNull(newCategoryId, "O novo ID da categoria não pode ser nulo");
        Objects.requireNonNull(newProduct, "O produto não pode ser nulo");

        var currentProduct = getProductFromCategoryById(currentCategoryId, newCategoryId);

        currentProduct.setDetails(newProduct.getDetails());
        currentProduct.setPrice(newProduct.getPrice());
        currentProduct.setImageUrl(newProduct.getImageUrl());
        currentProduct.setDisplayOrder(newProduct.getDisplayOrder());
        currentProduct.setActive(newProduct.isActive());
        currentProduct.markUpdatedNow();

        if (!currentCategoryId.equals(newCategoryId)) {
            removeProductFromCategory(currentCategoryId, newProduct);
            addProductToCategory(newCategoryId, newProduct);
        }
    }

    /**
     * Remove um produto de uma categoria.
     *
     * @param categoryId o ID da categoria
     * @param product o produto a ser removido
     */
    public void removeProductFromCategory(Long categoryId, Product product) {
        Objects.requireNonNull(categoryId, "O ID da categoria não pode ser nulo");
        Objects.requireNonNull(product, "O produto não pode ser nulo");

        var category = getCategoryById(categoryId);
        category.removeProduct(product);
    }

    /**
     * Atualiza a quantidade de estoque de um produto em uma categoria.
     *
     * @param categoryId o ID da categoria
     * @param productId o ID do produto
     * @param newQuantity a nova quantidade de estoque
     */
    public void updateProductStockQuantity(Long categoryId, Long productId, int newQuantity) {
        Objects.requireNonNull(categoryId, "O ID da categoria não pode ser nulo");
        Objects.requireNonNull(productId, "O ID produto não pode ser nulo");

        var product = getProductFromCategoryById(categoryId, productId);
        product.updateStockQuantity(newQuantity);
    }

    /**
     * Atualiza o campo updatedAt com o horário atual.
     *
     * @throws IllegalStateException se o horário atual for menor ou igual ao createdAt
     */
    public void markUpdatedNow() {
        this.auditInfo.setUpdatedAt(LocalDateTime.now());
    }
}