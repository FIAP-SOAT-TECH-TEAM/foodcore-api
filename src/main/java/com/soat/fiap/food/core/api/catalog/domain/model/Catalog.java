package com.soat.fiap.food.core.api.catalog.domain.model;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.ArrayList;
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
    private AuditInfo auditInfo;

    private List<Category> categories;

    public void addCategory(Category category) {

        Objects.requireNonNull(category, "A categoria não pode ser nula");

        categories = (categories == null) ? new ArrayList<>() : categories;

        if (categories
                .stream()
                .anyMatch(c -> c.getName().equals(category.getName()))) {
            return;
        }

        categories.add(category);
    }

    public void addProductToCategory(Long categoryId, Product product) {
        Objects.requireNonNull(categoryId, "O ID da categoria não pode ser nulo");
        Objects.requireNonNull(product, "O produto não pode ser nulo");


    }


} 