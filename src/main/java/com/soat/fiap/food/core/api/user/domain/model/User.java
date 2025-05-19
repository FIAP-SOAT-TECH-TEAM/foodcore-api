package com.soat.fiap.food.core.api.user.domain.model;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String email;
    private String document;
    private boolean active;
    private final AuditInfo auditInfo = new AuditInfo();

    
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