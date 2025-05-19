package com.soat.fiap.food.core.api.catalog.domain.model;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

import lombok.*;

/**
 * Entidade de domínio que representa um estoque de produto
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class Stock {
    private Long id;
    private Long customerId;
    private Integer quantity;
    private final AuditInfo auditInfo = new AuditInfo();

    private Product product;
}
