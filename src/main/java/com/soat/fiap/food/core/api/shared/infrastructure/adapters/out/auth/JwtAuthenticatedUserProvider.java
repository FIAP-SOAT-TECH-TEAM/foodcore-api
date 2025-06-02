package com.soat.fiap.food.core.api.shared.infrastructure.security.impl;

import com.soat.fiap.food.core.api.shared.exception.JwtException;
import com.soat.fiap.food.core.api.shared.infrastructure.security.AuthenticatedUserProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticatedUserProvider implements AuthenticatedUserProvider {

    @Override
    public Long getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null || !(authentication.getPrincipal() instanceof String)) {
            throw new JwtException("Usuário não autenticado com JWT");
        }

        Object details = authentication.getDetails();
        if (details instanceof Long id) {
            return id;
        }

        throw new JwtException("ID do usuário não encontrado no contexto de autenticação.");
    }

    @Override
    public String getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new JwtException("Usuário não autenticado com JWT");
        }

        return authentication.getAuthorities().stream()
                .findFirst()
                .map(GrantedAuthority::getAuthority)
                .map(role -> role.replace("ROLE_", ""))
                .orElseThrow(() -> new JwtException("Role não encontrada no token"));
        }
    }
