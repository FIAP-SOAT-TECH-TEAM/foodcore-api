package com.soat.fiap.food.core.api.shared.application.security;

public interface PaymentAccessManager {
    void validateAccess(Long paymentUserId);
}
