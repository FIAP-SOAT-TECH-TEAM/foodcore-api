package com.soat.fiap.food.core.api.product.domain.model;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

import lombok.*;

@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class Stock {
    private Long id;
    private Long customerId;
    private Integer quantity;
    private AuditInfo auditInfo;
}
