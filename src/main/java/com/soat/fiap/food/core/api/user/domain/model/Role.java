package com.soat.fiap.food.core.api.user.domain.model;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

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