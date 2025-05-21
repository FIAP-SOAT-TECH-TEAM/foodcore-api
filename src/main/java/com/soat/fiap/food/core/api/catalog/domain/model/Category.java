package com.soat.fiap.food.core.api.catalog.domain.model;

import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogException;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CategoryException;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.ProductException;
import com.soat.fiap.food.core.api.catalog.domain.vo.Details;
import com.soat.fiap.food.core.api.catalog.domain.vo.ImageUrl;
import com.soat.fiap.food.core.api.order.domain.model.OrderItem;
import com.soat.fiap.food.core.api.order.domain.vo.OrderNumber;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

import lombok.*;
import org.apache.commons.lang3.Validate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entidade de domínio que representa uma categoria de produto.
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class Category {
    private Long id;
    private Details details;
    private ImageUrl imageUrl;
    private Integer displayOrder;
    private boolean active = true;
    private final AuditInfo auditInfo = new AuditInfo();

    private Catalog catalog;
    private List<Product> products;

    /**
     * Construtor da categoria de produto.
     *
     * @param details      Detalhes da categoria (nome e descrição)
     * @param imageUrl     URL da imagem associada à categoria
     * @param displayOrder Ordem de exibição da categoria no catálogo
     * @param active       Indica se a categoria está ativa
     * @throws NullPointerException se {@code details} for nulo
     * @throws CatalogException     se {@code displayOrder} for menor que 0
     */
    public Category(
            Details details,
            ImageUrl imageUrl,
            Integer displayOrder,
            boolean active
    ) {
        validate(details, displayOrder);
        this.details = details;
        this.imageUrl = imageUrl;
        this.displayOrder = displayOrder;
        this.active = active;
    }

    /**
     * Valida os atributos obrigatórios da categoria.
     *
     * @param details      Detalhes da categoria
     * @param displayOrder Ordem de exibição
     * @throws NullPointerException se {@code details} for nulo
     * @throws CatalogException     se {@code displayOrder} for menor que 0
     */
    private void validate(Details details, Integer displayOrder) {
        Objects.requireNonNull(details, "Os detalhes da categoria não podem ser nulos");

        if (displayOrder != null && displayOrder < 0) {
            throw new CatalogException("A ordem de exibição da categoria deve ser maior que 0");
        }
    }

    /**
     * Fornece uma lista imutável de produtos
     * @return lista imutável de produtos
     */
    List<Product> getProducts() {
        return Collections.unmodifiableList(this.products);
    }

    /**
     * Retorna o nome da categoria.
     *
     * @return nome da categoria
     */
    String getName() {
        return this.details.name();
    }

    /**
     * Retorna a descrição da categoria.
     *
     * @return descrição da categoria
     */
    String getDescription() {
        return this.details.description();
    }

    /**
     * Retorna um produto da categoria com base no ID.
     *
     * @param productId ID do produto
     * @return produto correspondente
     * @throws NullPointerException se {@code productId} for nulo
     * @throws ProductException     se o produto não for encontrado
     */
    Product getProductById(Long productId) {
        Objects.requireNonNull(productId, "O ID do produto não pode ser nulo");

        return products.stream()
                .filter(p -> p.getId().equals(productId))
                .findFirst()
                .orElseThrow(() -> new ProductException("Produto não encontrado"));
    }

    /**
     * Adiciona um produto à categoria.
     *
     * @param product produto a ser adicionado
     * @throws NullPointerException se {@code product} for nulo
     * @throws CategoryException    se já existir um produto com o mesmo nome
     */
    void addProduct(Product product) {
        Objects.requireNonNull(product, "O produto não pode ser nulo");

        products = (products == null) ? new ArrayList<>() : products;

        if (products.stream().anyMatch(p -> p.getName().equals(product.getName()))) {
            throw new CategoryException(String.format("Já existe um produto cadastrado com o nome: %s, na categoria", product.getName()));
        }

        products.add(product);
    }

    /**
     * Remove um produto da categoria.
     *
     * @param product produto a ser removido
     * @throws NullPointerException se {@code product} for nulo
     * @throws CategoryException    se o produto não existir na categoria
     */
    void removeProduct(Product product) {
        Objects.requireNonNull(product, "O produto não pode ser nulo");

        if (products == null || products.stream().noneMatch(p -> p.getName().equals(product.getName()))) {
            throw new CategoryException("Produto não encontrado");
        }

        products.remove(product);
    }

    /**
     * Atualiza o campo updatedAt com o horário atual.
     *
     * @throws IllegalStateException se o horário atual for menor ou igual ao createdAt
     */
    void markUpdatedNow() {
        this.auditInfo.setUpdatedAt(LocalDateTime.now());
    }
}
