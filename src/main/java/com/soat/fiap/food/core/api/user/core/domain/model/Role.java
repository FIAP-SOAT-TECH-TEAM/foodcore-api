package com.soat.fiap.food.core.api.user.core.domain.model;

import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;

import lombok.Data;


/**
 * Entidade de domínio que representa uma role
 */
@Data
public class Role {
    private Long id;
    private String name;
    private String description;
    private AuditInfo auditInfo = new AuditInfo();


}