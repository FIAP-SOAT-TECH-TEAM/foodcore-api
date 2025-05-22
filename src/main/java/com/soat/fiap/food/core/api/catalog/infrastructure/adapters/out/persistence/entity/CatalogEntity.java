package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.out.persistence.entity;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
/**
 * Entidade JPA para catálogo
 */
@Entity
@Table(name = "catalog")
@Getter
@Setter
public class CatalogEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 100, nullable = false, unique = true)
    private String name;

    @Embedded
    private AuditInfo auditInfo = new AuditInfo();

    @OneToMany(mappedBy = "catalog",
            cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH},
            orphanRemoval = true)
    private List<CategoryEntity> categories = new ArrayList<>();
}
