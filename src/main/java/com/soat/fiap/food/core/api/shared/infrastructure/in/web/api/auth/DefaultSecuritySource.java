package com.soat.fiap.food.core.api.shared.infrastructure.in.web.api.auth;

import com.soat.fiap.food.core.api.shared.infrastructure.common.source.SecuritySource;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * Implementação concreta de {@link SecuritySource} utilizando
 * o {@link BCryptPasswordEncoder} para codificar senhas.
 */
@Component
@RequiredArgsConstructor
public class DefaultSecuritySource implements SecuritySource {

    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * {@inheritDoc}
     */
    @Override
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }
}
