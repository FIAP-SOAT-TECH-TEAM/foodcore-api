package com.soat.fiap.food.core.api.catalog.domain.model;

import lombok.*;
import java.math.BigDecimal;
import java.util.List;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

/**
 * Entidade de domínio que representa um produto
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class Product {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private boolean active;
    private Integer displayOrder;
    private AuditInfo auditInfo;

    private Category category;
    private List<Stock> stocks;

    /**
     * Ativa o produto
     */
    void activate() {
        this.active = true;
    }
    
    /**
     * Desativa o produto
     */
    void deactivate() {
        this.active = false;
    }

    /**
     * Verifica se o produto está disponível para venda.
     * 
     * @return true se o produto estiver ativo e com preço válido.
     */
    boolean isAvailable() {
        return active && price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }
} 