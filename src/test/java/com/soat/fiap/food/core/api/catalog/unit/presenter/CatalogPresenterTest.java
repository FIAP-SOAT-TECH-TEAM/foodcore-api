package com.soat.fiap.food.core.api.catalog.unit.presenter;

import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.presenter.web.api.CatalogPresenter;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("CatalogPresenter - Testes Unitários")
class CatalogPresenterTest {

    @Test
    @DisplayName("Deve converter catálogo para response")
    void shouldConvertCatalogToResponse() {
        // Arrange
        var catalog = CatalogFixture.createValidCatalog();

        // Act
        var response = CatalogPresenter.toCatalogResponse(catalog);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(catalog.getId());
        assertThat(response.getName()).isEqualTo(catalog.getName());
    }

    @Test
    @DisplayName("Deve converter catálogo com categorias para response")
    void shouldConvertCatalogWithCategoriesToResponse() {
        // Arrange
        var catalog = CatalogFixture.createCatalogWithMultipleProducts();

        // Act
        var response = CatalogPresenter.toCatalogResponse(catalog);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getId()).isEqualTo(catalog.getId());
        assertThat(response.getName()).isEqualTo(catalog.getName());
        assertThat(response.getCreatedAt()).isNotNull();
    }

    @Test
    @DisplayName("Deve executar conversão sem exceções")
    void shouldExecuteConversionWithoutExceptions() {
        // Arrange
        var catalog = CatalogFixture.createValidCatalog();

        // Act & Assert
        var response = CatalogPresenter.toCatalogResponse(catalog);
        
        assertThat(response).isNotNull();
        assertThat(response.getName()).isNotBlank();
    }
} 