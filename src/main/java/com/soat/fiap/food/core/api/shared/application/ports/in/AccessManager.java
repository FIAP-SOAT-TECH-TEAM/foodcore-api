package com.soat.fiap.food.core.api.shared.application.security;

public interface AccessManager {
    void validateAccess(Long paymentUserId);
}
