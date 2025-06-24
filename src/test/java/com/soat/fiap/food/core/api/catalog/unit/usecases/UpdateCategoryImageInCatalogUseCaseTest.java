package com.soat.fiap.food.core.api.catalog.unit.usecases;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.category.UpdateCategoryImageInCatalogUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.ImageStorageGateway;
import com.soat.fiap.food.core.api.shared.fixtures.CatalogFixture;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("UpdateCategoryImageInCatalogUseCase - Testes Unitários")
class UpdateCategoryImageInCatalogUseCaseTest {

    @Mock
    private CatalogGateway catalogGateway;

    @Mock
    private ImageStorageGateway imageStorageGateway;

    @Mock
    private MultipartFile imageFile;

    @Test
    @DisplayName("Deve atualizar imagem da categoria com sucesso")
    void shouldUpdateCategoryImageSuccessfully() {
        // Arrange
        var catalog = CatalogFixture.createCatalogWithCategories();
        var catalogId = 1L;
        var categoryId = 1L;
        var newImagePath = "categories/1/new-image.jpg";

        when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));
        when(imageFile.isEmpty()).thenReturn(false);
        when(imageStorageGateway.uploadImage(anyString(), eq(imageFile))).thenReturn(newImagePath);

        // Act
        var result = UpdateCategoryImageInCatalogUseCase.updateCategoryImageInCatalog(
                catalogId, categoryId, imageFile, catalogGateway, imageStorageGateway);

        // Assert
        assertThat(result).isNotNull();
        verify(catalogGateway).findById(catalogId);
        verify(imageStorageGateway).uploadImage("categories/" + categoryId, imageFile);
    }

    @Test
    @DisplayName("Deve excluir imagem anterior quando categoria já possui imagem")
    void shouldDeletePreviousImageWhenCategoryAlreadyHasImage() {
        // Arrange
        var catalog = CatalogFixture.createCatalogWithCategories();
        var category = catalog.getCategoryById(1L);
        category.setImageUrlValue("categories/1/old-image.jpg");
        
        var catalogId = 1L;
        var categoryId = 1L;
        var newImagePath = "categories/1/new-image.jpg";

        when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));
        when(imageFile.isEmpty()).thenReturn(false);
        when(imageStorageGateway.uploadImage(anyString(), eq(imageFile))).thenReturn(newImagePath);

        // Act
        var result = UpdateCategoryImageInCatalogUseCase.updateCategoryImageInCatalog(
                catalogId, categoryId, imageFile, catalogGateway, imageStorageGateway);

        // Assert
        assertThat(result).isNotNull();
        verify(imageStorageGateway).deleteImage("categories/1/old-image.jpg");
        verify(imageStorageGateway).uploadImage("categories/" + categoryId, imageFile);
    }

    @Test
    @DisplayName("Deve lançar exceção quando catálogo não for encontrado")
    void shouldThrowExceptionWhenCatalogNotFound() {
        // Arrange
        var catalogId = 999L;
        var categoryId = 1L;

        when(catalogGateway.findById(catalogId)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> UpdateCategoryImageInCatalogUseCase.updateCategoryImageInCatalog(
                catalogId, categoryId, imageFile, catalogGateway, imageStorageGateway))
                .isInstanceOf(CatalogNotFoundException.class);

        verify(catalogGateway).findById(catalogId);
        verifyNoInteractions(imageStorageGateway);
    }

    @Test
    @DisplayName("Deve lançar exceção quando arquivo de imagem for nulo")
    void shouldThrowExceptionWhenImageFileIsNull() {
        // Arrange
        var catalog = CatalogFixture.createCatalogWithCategories();
        var catalogId = 1L;
        var categoryId = 1L;

        when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));

        // Act & Assert
        assertThatThrownBy(() -> UpdateCategoryImageInCatalogUseCase.updateCategoryImageInCatalog(
                catalogId, categoryId, null, catalogGateway, imageStorageGateway))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O arquivo de imagem não pode ser vazio");

        verify(catalogGateway).findById(catalogId);
        verifyNoInteractions(imageStorageGateway);
    }

    @Test
    @DisplayName("Deve lançar exceção quando arquivo de imagem for vazio")
    void shouldThrowExceptionWhenImageFileIsEmpty() {
        // Arrange
        var catalog = CatalogFixture.createCatalogWithCategories();
        var catalogId = 1L;
        var categoryId = 1L;

        when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));
        when(imageFile.isEmpty()).thenReturn(true);

        // Act & Assert
        assertThatThrownBy(() -> UpdateCategoryImageInCatalogUseCase.updateCategoryImageInCatalog(
                catalogId, categoryId, imageFile, catalogGateway, imageStorageGateway))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("O arquivo de imagem não pode ser vazio");

        verify(catalogGateway).findById(catalogId);
        verifyNoInteractions(imageStorageGateway);
    }

    @Test
    @DisplayName("Deve lançar RuntimeException quando ocorrer erro no upload")
    void shouldThrowRuntimeExceptionWhenUploadErrorOccurs() {
        // Arrange
        var catalog = CatalogFixture.createCatalogWithCategories();
        var catalogId = 1L;
        var categoryId = 1L;

        when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));
        when(imageFile.isEmpty()).thenReturn(false);
        when(imageStorageGateway.uploadImage(anyString(), eq(imageFile)))
                .thenThrow(new RuntimeException("Erro no upload"));

        // Act & Assert
        assertThatThrownBy(() -> UpdateCategoryImageInCatalogUseCase.updateCategoryImageInCatalog(
                catalogId, categoryId, imageFile, catalogGateway, imageStorageGateway))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Falha ao processar imagem");

        verify(catalogGateway).findById(catalogId);
        verify(imageStorageGateway).uploadImage("categories/" + categoryId, imageFile);
    }

    @Test
    @DisplayName("Deve manter propriedades da categoria inalteradas exceto a imagem")
    void shouldKeepCategoryPropertiesUnchangedExceptImage() {
        // Arrange
        var catalog = CatalogFixture.createCatalogWithCategories();
        var categoryId = 1L;
        var category = catalog.getCategoryById(categoryId);
        var originalName = category.getName();
        var originalDescription = category.getDescription();
        
        var catalogId = 1L;
        var newImagePath = "categories/1/new-image.jpg";

        when(catalogGateway.findById(catalogId)).thenReturn(Optional.of(catalog));
        when(imageFile.isEmpty()).thenReturn(false);
        when(imageStorageGateway.uploadImage(anyString(), eq(imageFile))).thenReturn(newImagePath);

        // Act
        var result = UpdateCategoryImageInCatalogUseCase.updateCategoryImageInCatalog(
                catalogId, categoryId, imageFile, catalogGateway, imageStorageGateway);

        // Assert
        assertThat(result).isNotNull();
        var updatedCategory = result.getCategoryById(categoryId);
        assertThat(updatedCategory.getName()).isEqualTo(originalName);
        assertThat(updatedCategory.getDescription()).isEqualTo(originalDescription);
        assertThat(updatedCategory.getImageUrlValue()).isEqualTo(newImagePath);
    }
} 