package com.soat.fiap.food.core.api.product.domain.model;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

import lombok.*;

/**
 * Entidade de domínio que representa uma categoria de produto
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class Category {
    private Long id;
    private String name;
    private String description;
    private String imageUrl;
    private Integer displayOrder;
    private boolean active;
    private AuditInfo auditInfo;
    
    /**
     * Ativa a categoria
     */
    public void activate() {
        this.active = true;
    }
    
    /**
     * Desativa a categoria
     */
    public void deactivate() {
        this.active = false;
    }
} 