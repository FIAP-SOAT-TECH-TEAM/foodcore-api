package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.controller.web.api.product;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.UpdateProductImageInCategoryUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.DataSource;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.ImageStorageGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.ImageDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

/**
 * Controller: Atualizar imagem do produto.
 *
 */
@Slf4j
public class UpdateProductImageController {
    /**
     * Atualiza apenas a imagem de um produto existente.
     *
     * @param catalogId ID do catálogo
     * @param categoryId ID da categoria do produto
     * @param productId ID do produto
     * @param imageFile Arquivo da nova imagem
     * @param dataSource Origem de dados para o gateway
     * @param imageDataSource Origem de dados de imagem para o gateway
     * @throws CatalogNotFoundException se o catálogo não for encontrado
     * @throws IllegalArgumentException se o arquivo de imagem for nulo ou vazio
     * @throws RuntimeException se ocorrer um erro durante o upload da imagem
     */
    public static void updateProductImage(Long catalogId, Long categoryId, Long productId, MultipartFile imageFile, DataSource dataSource, ImageDataSource imageDataSource) {
        log.debug("Atualizando imagem do produto ID: {}", productId);

        var catalogGateway = new CatalogGateway(dataSource);

        var imageStorageGateway = new ImageStorageGateway(imageDataSource);

        var catalog = UpdateProductImageInCategoryUseCase.updateProductImageInCategory(catalogId, categoryId, productId, imageFile, catalogGateway, imageStorageGateway);

        catalogGateway.save(catalog);
    }
}
