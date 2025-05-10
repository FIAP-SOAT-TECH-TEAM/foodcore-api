package com.soat.fiap.food.core.api.customer.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * Entidade de domínio que representa um cliente
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Long id;
    private String name;
    private String email;
    private String document;
    private String phone;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private boolean active;
    
    /**
     * Ativa o cliente
     */
    public void activate() {
        this.active = true;
    }
    
    /**
     * Desativa o cliente
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