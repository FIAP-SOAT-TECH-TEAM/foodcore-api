package com.soat.fiap.food.core.api.shared.fixtures;

import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;
import com.soat.fiap.food.core.api.user.core.domain.model.User;
import com.soat.fiap.food.core.api.user.core.domain.vo.RoleType;

import java.time.LocalDateTime;

/**
 * Fixture para criação de objetos User para testes unitários
 */
public class UserFixture {

    public static User createValidUser() {
        return new User(
                false,
                "João Silva",
                "joao.silva",
                "joao@email.com",
                "senha123456",
                "12345678901"
        );
    }

    public static User createGuestUser() {
        return new User(
                true,
                "Usuário Convidado",
                null,
                null,
                null,
                null
        );
    }

    public static User createUserWithEmail() {
        return new User(
                false,
                "Maria Santos",
                null,
                "maria@email.com",
                "senha123456",
                null
        );
    }

    public static User createUserWithDocument() {
        return new User(
                false,
                "Pedro Oliveira",
                null,
                null,
                "senha123456",
                "98765432100"
        );
    }

    public static User createUserWithUsername() {
        return new User(
                false,
                "Ana Costa",
                "ana.costa",
                null,
                "senha123456",
                null
        );
    }

    public static User createUserWithCreationDate(LocalDateTime createdAt) {
        var user = new User(
                false,
                "João Silva",
                "joao.silva",
                "joao@email.com",
                "senha123456",
                "12345678901"
        ) {
            @Override
            public LocalDateTime getCreatedAt() {
                return createdAt;
            }
        };
        
        return user;
    }
} 