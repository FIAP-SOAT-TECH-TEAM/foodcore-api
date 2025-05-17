package com.soat.fiap.food.core.api.shared.vo;

import lombok.Getter;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Objeto de valor que representa informações de auditoria
 * - createdAt: data e hora de criação
 * - updatedAt: data e hora da última atualização
 */
@Getter
public class AuditInfo {
    private final LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime updatedAt = LocalDateTime.now();

    /**
     * Construtor padrão
     * 
     * @param updatedAt LocalDateTime a ser criado
     * @throws NullPointerException  se o updatedAt for nulo
     * @throws IllegalStateException se o updatedAt for menor ou igual a createdAt
     */
    public AuditInfo(LocalDateTime updatedAt) {
        validate(updatedAt);
        this.updatedAt = updatedAt;
    }

    /**
     * Autualiza o campo updatedAt
     * 
     * @param updatedAt LocalDateTime a ser atualizado
     * @throws NullPointerException  se o updatedAt for nulo
     * @throws IllegalStateException se o updatedAt for menor ou igual a createdAt
     */
    public void setUpdatedAt(LocalDateTime updatedAt) {
        validate(updatedAt);
        this.updatedAt = updatedAt;
    }

    /**
     * Valida o campo updatedAt
     * 
     * @param updatedAt data e hora da última atualização
     * @throws NullPointerException  se o updatedAt for nulo
     * @throws IllegalStateException se o updatedAt for menor ou igual a createdAt
     */
    private void validate(LocalDateTime updatedAt) {
        Objects.requireNonNull(updatedAt, "UpdatedAt não pode ser nulo");

        if (updatedAt.isBefore(createdAt) || updatedAt.isEqual(createdAt)) {
            throw new IllegalArgumentException("UpdatedAt não pode ser menor ou igual a CreatedAt");
        }
    }
}