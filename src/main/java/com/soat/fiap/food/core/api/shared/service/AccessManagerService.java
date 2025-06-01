package com.soat.fiap.food.core.api.shared.application.impl;

import com.soat.fiap.food.core.api.shared.application.security.PaymentAccessManager;
import com.soat.fiap.food.core.api.shared.infrastructure.security.AuthenticatedUserProvider;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

@Component
public class AccessManagerService implements PaymentAccessManager {

    private final AuthenticatedUserProvider userProvider;

    public AccessManagerService(AuthenticatedUserProvider userProvider) {
        this.userProvider = userProvider;
    }

    @Override
    public void validateAccess(Long paymentUserId) {
        Long currentUserId = userProvider.getUserId();
        if (currentUserId == null) {
            throw new AccessDeniedException("Usuário não autenticado.");
        }
        String role = userProvider.getUserRole();


        if (!currentUserId.equals(paymentUserId) && !"ADMIN".equalsIgnoreCase(role)) {
            throw new AccessDeniedException("Você não tem permissão para visualizar este pagamento.");
        }
    }
}
