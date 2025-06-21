package com.soat.fiap.food.core.api.user.core.domain.model;

import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserException;

import lombok.Data;

import java.time.LocalDateTime;

/**
 * Entidade de domínio que representa um usuário
 * AGGREGATE ROOT:
 * - Toda modificação de entidades internas do agregado devem passar pela
 * entidade raiz;
 * - Único ponto de entrada para qualquer entidade interna do agregado (Lei de
 * Demeter);
 * - Entidades dentro deste agregado podem se referenciar via id ou objeto;
 * - Entidades de outros agregados só podem referenciar esta entidade raiz, e
 * isto deve ser via Id;
 */
@Data
public class User {
    private Long id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String document;
    private boolean active = true;
    private boolean guest;
    private Role role = new Role();
    private LocalDateTime lastLogin;
    private transient String token;
    private AuditInfo auditInfo = new AuditInfo();

    public User(
            boolean guest,
            String name,
            String username,
            String email,
            String password,
            String document
    ) {
        validateInternalState();

        this.guest = guest;
        this.name = name;
        this.username = username;
        this.email = email;
        this.password = password;
        this.document = document;
    }

    /**
     * Verifica se o usuário é um convidado (guest)
     * @return true se for um usuário convidado, false caso contrário
     */
    public boolean isGuest() {
        return (this.document == null || this.document.isBlank()) &&
                (this.email == null || this.email.isBlank()) &&
                (this.username == null || this.username.isBlank()) || this.guest;
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

    /**
     * Verifica se o usuário tem um username
     * @return true se tiver, false caso contrário
     */
    public boolean hasUsername() {
        return this.username != null && !this.username.isBlank();
    }


    /**
     * Verifica se o usuário tem um email
     * @return true se tiver, false caso contrário
     */
    public boolean hasEmail() {
        return this.email != null && !this.email.isBlank();
    }

    /**
     * Verifica se o usuário tem uma senha
     * @return true se tiver, false caso contrário
     */
    public boolean hasPassword() {
        return this.password != null && !this.password.isBlank();
    }

    /**
     * Verifica se o usuário tem um documento
     * @return true se tiver, false caso contrário
     */

    public boolean hasDocument() {
        return this.document != null && !this.document.isBlank();
    }

    /**
     * Valida o estado interno do usuário garantindo que os dados estão corretos
     */
    public void validateInternalState() {
        if (hasDocument() && !isValidDocument()) {
            throw new UserException("Documento inválido");
        }
        if (hasDocument() && this.document.length() < 11) {
            throw new UserException("O documento deve ter pelo menos 11 caracteres");
        }
        if (hasEmail()) {
            if (!this.email.contains("@")) {
                throw new UserException("Email inválido");
            }
            if (this.name == null || this.name.isBlank()) {
                throw new UserException("Nome é obrigatório");
            }
        }
        if (hasUsername() && this.username.length() < 3) {
            throw new UserException("O username deve ter pelo menos 3 caracteres");
        }
        if (hasPassword() && this.password.length() < 8) {
            throw new UserException("A senha deve ter pelo menos 8 caracteres");
        }
    }

    /**
     * Retorna a data de criação do usuário
     * @return a data de criação de usuário
     */
    public LocalDateTime getCreatedAt() {
        return this.auditInfo.getCreatedAt();
    }

}