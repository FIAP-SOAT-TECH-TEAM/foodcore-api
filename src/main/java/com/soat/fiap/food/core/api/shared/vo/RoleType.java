package com.soat.fiap.food.core.api.shared.vo;

public enum RoleType {
    ADMIN(1),
    USER(2);

    private final int id;

    RoleType(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public static RoleType fromId(int id) {
        for (RoleType role : values()) {
            if (role.id == id) return role;
        }
        throw new IllegalArgumentException("Role inválida: " + id);
    }
}