package com.soat.fiap.food.core.api.catalog.domain.model;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;
import lombok.Data;
import lombok.Getter;

import java.math.BigDecimal;
import java.util.List;

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


} 