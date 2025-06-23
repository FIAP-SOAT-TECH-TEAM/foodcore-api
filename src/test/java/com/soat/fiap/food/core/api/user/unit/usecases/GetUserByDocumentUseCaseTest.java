package com.soat.fiap.food.core.api.user.unit.usecases;

import com.soat.fiap.food.core.api.shared.fixtures.UserFixture;
import com.soat.fiap.food.core.api.user.core.application.usecases.GetUserByDocumentUseCase;
import com.soat.fiap.food.core.api.user.core.domain.exceptions.UserNotFoundException;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("GetUserByDocumentUseCase - Testes Unitários")
class GetUserByDocumentUseCaseTest {

    @Mock
    private UserGateway userGateway;

    @Test
    @DisplayName("Deve retornar usuário quando encontrado por documento")
    void shouldReturnUserWhenFoundByDocument() {
        // Arrange
        var user = UserFixture.createValidUser();
        user.setId(1L);
        var document = "12345678901";
        
        when(userGateway.findByDocument(document)).thenReturn(Optional.of(user));

        // Act
        var result = GetUserByDocumentUseCase.getUserByDocument(document, userGateway);

        // Assert
        assertNotNull(result);
        assertEquals(1L, result.getId());
        assertEquals(user.getName(), result.getName());
        assertEquals(user.getEmail(), result.getEmail());
        assertEquals(user.getDocument(), result.getDocument());
    }

    @Test
    @DisplayName("Deve lançar exceção quando usuário não for encontrado por documento")
    void shouldThrowExceptionWhenUserNotFoundByDocument() {
        // Arrange
        var document = "99999999999";
        when(userGateway.findByDocument(document)).thenReturn(Optional.empty());

        // Act & Assert
        var exception = assertThrows(UserNotFoundException.class,
                () -> GetUserByDocumentUseCase.getUserByDocument(document, userGateway));

        assertEquals("Usuário não encontrado com Documento: 99999999999", exception.getMessage());
    }

    @Test
    @DisplayName("Deve retornar usuário com documento válido")
    void shouldReturnUserWithValidDocument() {
        // Arrange
        var user = UserFixture.createUserWithDocument();
        user.setId(2L);
        var validDocument = "98765432100";
        
        when(userGateway.findByDocument(validDocument)).thenReturn(Optional.of(user));

        // Act
        var result = GetUserByDocumentUseCase.getUserByDocument(validDocument, userGateway);

        // Assert
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertNotNull(result.getDocument());
        assertEquals(validDocument, result.getDocument());
    }
} 