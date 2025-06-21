package com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.auth;


import com.soat.fiap.food.core.api.shared.infrastructure.common.source.AccessManagerSource;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.AuthenticatedUserSource;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;

/**
 * Implementação concreta: DataSource para validação de acesso com base no ID e papel do usuário autenticado.
 */
@Component
public class DefaultAccessManagerSource implements AccessManagerSource {

    private final AuthenticatedUserSource userProvider;

    /**
     * Construtor do serviço de validação de acesso.
     *
     * @param userProvider Provedor de dados do usuário autenticado.
     */
    public DefaultAccessManagerSource(AuthenticatedUserSource userProvider) {
        this.userProvider = userProvider;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void validateAccess(Long userId) {
        Long currentUserId = userProvider.getUserId();
        if (currentUserId == null) {
            throw new AccessDeniedException("Usuário não autenticado.");
        }
        String role = userProvider.getUserRole();

        if (!currentUserId.equals(userId) && !"ADMIN".equalsIgnoreCase(role)) {
            throw new AccessDeniedException("Você não tem permissão para visualizar este recurso.");
        }
    }
}