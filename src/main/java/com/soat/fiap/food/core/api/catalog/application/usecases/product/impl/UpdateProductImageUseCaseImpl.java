package com.soat.fiap.food.core.api.catalog.application.usecases.product.impl;

import com.soat.fiap.food.core.api.catalog.application.usecases.product.UpdateProductImageUseCase;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.interfaces.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.shared.application.ports.out.ImageStorageGateway;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * Caso de uso (implementação concreta): Atualizar imagem do produto.
 *
 */
@Slf4j
public class UpdateProductImageUseCaseImpl implements UpdateProductImageUseCase {

    private final CatalogGateway catalogGateway;
    private final ImageStorageGateway imageStorageGateway;

    public UpdateProductImageUseCaseImpl(
            CatalogGateway catalogRepository,
            ImageStorageGateway imageStorageGateway
    ) {
        this.catalogGateway = catalogRepository;
        this.imageStorageGateway = imageStorageGateway;
    }

    /**
     * Atualiza apenas a imagem de um produto existente.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria do produto
     * @param productId ID do produto
     * @param imageFile Arquivo da nova imagem
     * @throws CatalogNotFoundException se o catálogo não for encontrado
     * @throws IllegalArgumentException se o arquivo de imagem for nulo ou vazio
     * @throws RuntimeException se ocorrer um erro durante o upload da imagem
     */
    @Override
    public void updateProductImage(Long catalogId, Long categoryId, Long productId, MultipartFile imageFile) {
        log.debug("Atualizando imagem do produto ID: {}", productId);

        var catalog = catalogGateway.findById(catalogId);

        if (catalog.isEmpty()) {
            log.warn("Tentativa de excluir produto com catálogo inexistente. Id: {}", catalogId);
            throw new CatalogNotFoundException("Catalogo", catalogId);
        }

        if (imageFile == null || imageFile.isEmpty()) {
            log.warn("Tentativa de upload de imagem com arquivo vazio ou nulo");
            throw new IllegalArgumentException("O arquivo de imagem não pode ser vazio");
        }

        var product = catalog.get().getProductFromCategoryById(categoryId, productId);

        try {

            log.debug("Processando upload de imagem para produto ID: {}", productId);

            if (product.getImageUrl() != null && !product.imageUrlIsEmpty()) {
                String currentImagePath = product.getImageUrlValue();
                log.debug("Removendo imagem anterior: {}", currentImagePath);
                imageStorageGateway.deleteImage(currentImagePath);
            }

            String storagePath = "products/" + productId;
            String imagePath = imageStorageGateway.uploadImage(storagePath, imageFile);
            log.debug("Nova imagem enviada para o caminho: {}", imagePath);

            product.setImageUrlValue(imagePath);

            catalog.get().updateProductInCategory(categoryId, product);
            catalogGateway.save(catalog.get());

        } catch (Exception e) {
            log.error("Erro ao processar upload de imagem: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao processar imagem: " + e.getMessage(), e);
        }
    }
}
