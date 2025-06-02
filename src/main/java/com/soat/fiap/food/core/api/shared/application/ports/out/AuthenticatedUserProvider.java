package com.soat.fiap.food.core.api.shared.application.ports.out;

public interface AuthenticatedUserProvider {
    Long getUserId();
    String getUserRole();
}
