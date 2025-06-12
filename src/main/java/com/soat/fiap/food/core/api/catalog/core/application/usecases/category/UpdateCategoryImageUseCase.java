package com.soat.fiap.food.core.api.catalog.core.application.usecases.category;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.interfaces.gateways.ImageStorageGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * Caso de uso: Atualizar imagem da categoria.
 *
 */
@Slf4j
public class UpdateCategoryImageUseCase {

    private final CatalogGateway catalogGateway;
    private final ImageStorageGateway imageStorageGateway;

    public UpdateCategoryImageUseCase(
            CatalogGateway catalogGateway,
            ImageStorageGateway imageStorageGateway
    ) {
        this.catalogGateway = catalogGateway;
        this.imageStorageGateway = imageStorageGateway;
    }

    /**
     * Atualiza apenas a imagem de uma categoria existente.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria do categoria
     * @param imageFile Arquivo da nova imagem
     * @throws CatalogNotFoundException se o catálogo não for encontrado
     * @throws IllegalArgumentException se o arquivo de imagem for nulo ou vazio
     * @throws RuntimeException se ocorrer um erro durante o upload da imagem
     */
    public void updateCategoryImage(Long catalogId, Long categoryId, MultipartFile imageFile) {
        log.debug("Atualizando imagem do categoria ID: {}", categoryId);

        var catalog = catalogGateway.findById(catalogId);

        if (catalog.isEmpty()) {
            log.warn("Tentativa de excluir categoria com catálogo inexistente. Id: {}", catalogId);
            throw new CatalogNotFoundException("Catalogo", catalogId);
        }

        if (imageFile == null || imageFile.isEmpty()) {
            log.warn("Tentativa de upload de imagem com arquivo vazio ou nulo");
            throw new IllegalArgumentException("O arquivo de imagem não pode ser vazio");
        }

        var category = catalog.get().getCategoryById(categoryId);

        try {

            log.debug("Processando upload de imagem para categoria ID: {}", categoryId);

            if (category.getImageUrl() != null && !category.imageUrlIsEmpty()) {
                var currentImagePath = category.getImageUrlValue();
                log.debug("Removendo imagem anterior: {}", currentImagePath);
                imageStorageGateway.deleteImage(currentImagePath);
            }

            String storagePath = "categories/" + categoryId;
            String imagePath = imageStorageGateway.uploadImage(storagePath, imageFile);
            log.debug("Nova imagem enviada para o caminho: {}", imagePath);

            category.setImageUrlValue(imagePath);

            catalog.get().updateCategory(category);
            catalogGateway.save(catalog.get());

        } catch (Exception e) {
            log.error("Erro ao processar upload de imagem: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao processar imagem: " + e.getMessage(), e);
        }
    }
}
