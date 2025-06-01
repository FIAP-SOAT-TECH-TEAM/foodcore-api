package com.soat.fiap.food.core.api.shared.infrastructure.security;

public interface AuthenticatedUserProvider {
    Long getUserId();
    String getUserRole();
}
