package com.soat.fiap.food.core.api.product.domain.model;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stock {
    private Long id;
    private Long customerId;
    private Integer quantity;
    private AuditInfo auditInfo;
}
