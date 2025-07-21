package com.soat.fiap.food.core.api.user.core.domain.vo;

import lombok.Getter;

@Getter
public enum RoleType {
	ADMIN(1, "ADMIN"), USER(2, "USER"), GUEST(3, "GUEST");

	private final int id;
	private final String name;

	RoleType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static RoleType fromId(int id) {
		for (RoleType role : values()) {
			if (role.id == id)
				return role;
		}
		throw new IllegalArgumentException("Role inválida: " + id);
	}
}
