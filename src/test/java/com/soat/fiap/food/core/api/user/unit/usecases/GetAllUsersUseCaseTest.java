package com.soat.fiap.food.core.api.user.unit.usecases;

import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import com.soat.fiap.food.core.api.user.core.application.usecases.GetAllUsersUseCase;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetAllUsersUseCase - Testes Unitários")
class GetAllUsersUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Test
    @DisplayName("Deve retornar todos os usuários quando existem usuários")
    void shouldReturnAllUsersWhenUsersExist() {
        // Arrange
        var user1 = UserFixture.createValidUser();
        user1.setId(1L);
        var user2 = UserFixture.createUserWithDocument();
        user2.setId(2L);
        var users = Arrays.asList(user1, user2);
        
        when(userGateway.findAll()).thenReturn(users);

        // Act
        var result = GetAllUsersUseCase.getAllUsers(userGateway);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(2);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(1).getId()).isEqualTo(2L);
        assertThat(result.get(0).getDocument()).isEqualTo("12345678901");
        assertThat(result.get(1).getDocument()).isEqualTo("98765432100");
        
        verify(userGateway).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não existem usuários")
    void shouldReturnEmptyListWhenNoUsersExist() {
        // Arrange
        when(userGateway.findAll()).thenReturn(Collections.emptyList());

        // Act
        var result = GetAllUsersUseCase.getAllUsers(userGateway);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        
        verify(userGateway).findAll();
    }

    @Test
    @DisplayName("Deve retornar usuário único quando existe apenas um")
    void shouldReturnSingleUserWhenOnlyOneExists() {
        // Arrange
        var user = UserFixture.createValidUser();
        user.setId(1L);
        var users = Collections.singletonList(user);
        
        when(userGateway.findAll()).thenReturn(users);

        // Act
        var result = GetAllUsersUseCase.getAllUsers(userGateway);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.get(0).getId()).isEqualTo(1L);
        assertThat(result.get(0).getName()).isEqualTo("João Silva");
        
        verify(userGateway).findAll();
    }

    @Test
    @DisplayName("Deve retornar múltiplos usuários com propriedades completas")
    void shouldReturnMultipleUsersWithCompleteProperties() {
        // Arrange
        var user1 = UserFixture.createValidUser();
        user1.setId(1L);
        var user2 = UserFixture.createUserWithEmail();
        user2.setId(2L);
        var user3 = UserFixture.createUserWithDocument();
        user3.setId(3L);
        var users = Arrays.asList(user1, user2, user3);
        
        when(userGateway.findAll()).thenReturn(users);

        // Act
        var result = GetAllUsersUseCase.getAllUsers(userGateway);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        
        // Verificar que todos os usuários têm propriedades válidas
        result.forEach(user -> {
            assertThat(user.getId()).isNotNull();
            assertThat(user.getName()).isNotBlank();
            assertThat(user.getCreatedAt()).isNotNull();
        });
        
        verify(userGateway).findAll();
    }

    @Test
    @DisplayName("Deve manter ordem dos usuários retornada pelo gateway")
    void shouldMaintainOrderOfUsersReturnedByGateway() {
        // Arrange
        var user1 = UserFixture.createValidUser();
        user1.setId(10L);
        var user2 = UserFixture.createUserWithEmail();
        user2.setId(5L);
        var user3 = UserFixture.createUserWithUsername();
        user3.setId(15L);
        var users = Arrays.asList(user1, user2, user3); // Ordem específica
        
        when(userGateway.findAll()).thenReturn(users);

        // Act
        var result = GetAllUsersUseCase.getAllUsers(userGateway);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        // Verificar que a ordem é mantida
        assertThat(result.get(0).getId()).isEqualTo(10L);
        assertThat(result.get(1).getId()).isEqualTo(5L);
        assertThat(result.get(2).getId()).isEqualTo(15L);
        
        verify(userGateway).findAll();
    }
} 