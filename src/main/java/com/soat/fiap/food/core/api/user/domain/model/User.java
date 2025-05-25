package com.soat.fiap.food.core.api.user.domain.model;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

import com.soat.fiap.food.core.api.shared.vo.RoleType;
import lombok.Data;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.time.LocalDateTime;

/**
 * Entidade de domínio que representa um usuário
 * AGGREGATE ROOT:
 *  - Toda modificação de entidades internas do agregado devem passar pela entidade raíz;
 *  - Único ponto de entrada para qualquer entidade interna do agregado (Lei de Demeter);
 *  - Entidades dentro deste agregado podem se referenciar via id ou objeto;
 *  - Entidades de outros agregados só podem referenciar esta entidade raiz, e isto deve ser via Id;
 */
@Data
public class User {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String document;
    private boolean active;
    private boolean guest;
    private Role role;
    private LocalDateTime lastLogin;
    private AuditInfo auditInfo = new AuditInfo();

    /**
     * Marca o usuário como convidado (guest)
     */
    public void markAsGuest() {
        this.guest = true;

        String randomId = generateShortRandomId(8);
        this.name = "guest-" + randomId;
        this.username = this.name;
        this.email = this.name + "@foodcore.local";

        String rawPassword = generateShortRandomId(12);
        this.password = rawPassword;

        this.active = true;

        // Define a role como GUEST
        Role guestRole = new Role();
        guestRole.setId((long) RoleType.GUEST.getId());
        guestRole.setName(RoleType.GUEST.name());
        this.role = guestRole;

    }

    /**
     * Gera um ID aleatório curto
     * @param length Comprimento do ID
     * @return ID aleatório gerado
     */

    private static String generateShortRandomId(int length) {
        String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sb = new StringBuilder(length);
        java.util.Random random = new java.util.Random();
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(random.nextInt(chars.length())));
        }
        return sb.toString();
    }

    /**
     * Verifica se o usuário é um convidado (guest)
     * @return true se for um usuário convidado, false caso contrário
     */

    public boolean isGuest() {
        return (this.document == null || this.document.isBlank()) &&
                (this.email == null || this.email.isBlank()) &&
                (this.username == null || this.username.isBlank());
    }


    /**
     * Ativa o usuário
     */
    public void activate() {
        this.active = true;
    }
    
    /**
     * Desativa o usuário
     */
    public void deactivate() {
        this.active = false;
    }
    
    /**
     * Verifica se o DOCUMENT é válido (implementação simples)
     * @return true se válido, false caso contrário
     */
    public boolean isValidDocument() {
        if (document == null || document.isBlank()) {
            return false;
        }

        String numericDocument = document.replaceAll("\\D", "");

        return numericDocument.length() == 11;
    }
} 