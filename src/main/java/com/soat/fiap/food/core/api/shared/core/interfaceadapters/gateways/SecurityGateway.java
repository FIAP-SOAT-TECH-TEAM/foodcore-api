package com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways;

import com.soat.fiap.food.core.api.shared.infrastructure.common.source.SecuritySource;
import lombok.extern.slf4j.Slf4j;

/**
 * Gateway para operações de segurança, abstraindo codificação de senha.
 */
@Slf4j
public class SecurityGateway {

    private final SecuritySource securitySource;

    public SecurityGateway(SecuritySource securitySource) {
        this.securitySource = securitySource;
    }

    /**
     * Codifica uma senha em texto puro para sua versão segura.
     *
     * @param rawPassword senha em texto puro
     * @return senha codificada (hash)
     */
    public String encodePassword(String rawPassword) {
        var encoded = securitySource.encodePassword(rawPassword);
        log.debug("Senha codificada via gateway");
        return encoded;
    }

    /**
     * Verifica se a senha em texto puro corresponde à senha codificada.
     *
     * @param rawPassword senha em texto puro
     * @param encodedPassword senha codificada
     * @return true se as senhas corresponderem, false caso contrário
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        var result = securitySource.matches(rawPassword, encodedPassword);
        log.debug("Resultado da verificação de senha via gateway: {}", result);
        return result;
    }
}