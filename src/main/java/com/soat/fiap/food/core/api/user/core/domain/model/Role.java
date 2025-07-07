package com.soat.fiap.food.core.api.user.core.domain.model;

import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;
import com.soat.fiap.food.core.api.user.core.domain.vo.RoleType;

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

	/**
	 * Cria uma instância padrão de Role com o tipo USER.
	 */
	public static Role defaultRole() {
		Role role = new Role();
		role.setId((long) RoleType.USER.getId());
		role.setName(RoleType.USER.name());
		return role;
	}
}
