package com.soat.fiap.food.core.api.user.infrastructure.out.persistence.postgres.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entidade JPA para roles
 */
@Entity @Table(name = "roles") @Data @Builder @NoArgsConstructor @AllArgsConstructor
public class RoleEntity {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(nullable = false)
	private String name;

	@Column(nullable = false)
	private String description;

	@Column(nullable = false)
	private LocalDateTime createdAt;

	@Column(nullable = false)
	private LocalDateTime updatedAt;

}
