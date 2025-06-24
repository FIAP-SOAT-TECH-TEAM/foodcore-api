package com.soat.fiap.food.core.api.user.unit.controller;

import com.soat.fiap.food.core.api.user.core.interfaceadapters.controller.web.api.GetAllUsersController;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetAllUsersController - Testes Unitários")
class GetAllUsersControllerTest {

    @Mock
    private UserDataSource userDataSource;

    @Test
    @DisplayName("Deve buscar todos os usuários com sucesso")
    void shouldFetchAllUsersSuccessfully() {
        // Arrange
        var users = List.of(
            UserFixture.createValidUser(),
            UserFixture.createUserWithEmail(),
            UserFixture.createUserWithDocument()
        );
        
        when(userDataSource.findAll()).thenReturn(users);

        // Act
        var result = GetAllUsersController.getAllUsers(userDataSource);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(3);
        assertThat(result.get(0).getName()).isEqualTo(users.get(0).getName());
        assertThat(result.get(1).getName()).isEqualTo(users.get(1).getName());
        assertThat(result.get(2).getName()).isEqualTo(users.get(2).getName());
        
        verify(userDataSource).findAll();
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando não há usuários")
    void shouldReturnEmptyListWhenNoUsersExist() {
        // Arrange
        when(userDataSource.findAll()).thenReturn(List.of());

        // Act
        var result = GetAllUsersController.getAllUsers(userDataSource);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).isEmpty();
        
        verify(userDataSource).findAll();
    }

    @Test
    @DisplayName("Deve verificar se controller não lança exceção ao ser instanciado")
    void shouldVerifyControllerDoesNotThrowExceptionWhenInstantiated() {
        // Act & Assert
        assertDoesNotThrow(() -> new GetAllUsersController());
    }

    @Test
    @DisplayName("Deve processar lista com apenas um usuário")
    void shouldProcessListWithOnlyOneUser() {
        // Arrange
        var user = UserFixture.createValidUser();
        var users = List.of(user);
        
        when(userDataSource.findAll()).thenReturn(users);

        // Act
        var result = GetAllUsersController.getAllUsers(userDataSource);

        // Assert
        assertThat(result).isNotNull();
        assertThat(result).hasSize(1);
        assertThat(result.getFirst().getName()).isEqualTo(user.getName());
        
        verify(userDataSource).findAll();
    }
} 