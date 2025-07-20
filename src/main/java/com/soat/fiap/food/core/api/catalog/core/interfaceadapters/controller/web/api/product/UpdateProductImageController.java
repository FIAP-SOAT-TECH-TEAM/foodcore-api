package com.soat.fiap.food.core.api.catalog.core.interfaceadapters.controller.web.api.product;

import com.soat.fiap.food.core.api.catalog.core.application.usecases.product.UpdateProductImageInCategoryUseCase;
import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.CatalogNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.presenter.web.api.ProductPresenter;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.ProductResponse;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.dto.FileUploadDTO;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.ImageStorageGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.ImageDataSource;

import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Atualizar imagem do produto.
 */
@Slf4j
public class UpdateProductImageController {
	/**
	 * Atualiza apenas a imagem de um produto existente.
	 *
	 * @param catalogId
	 *            ID do catálogo
	 * @param categoryId
	 *            ID da categoria do produto
	 * @param productId
	 *            ID do produto
	 * @param imageFile
	 *            Arquivo da nova imagem
	 * @param catalogDataSource
	 *            Origem de dados para o gateway
	 * @param imageDataSource
	 *            Origem de dados de imagem para o gateway
	 * @throws CatalogNotFoundException
	 *             se o catálogo não for encontrado
	 * @throws IllegalArgumentException
	 *             se o arquivo de imagem for nulo ou vazio
	 * @throws RuntimeException
	 *             se ocorrer um erro durante o upload da imagem
	 */
	public static Product updateProductImage(Long catalogId, Long categoryId, Long productId, FileUploadDTO imageFile,
			CatalogDataSource catalogDataSource, ImageDataSource imageDataSource) {
		return executeUpdate(catalogId, categoryId, productId, imageFile, catalogDataSource, imageDataSource);
	}

	/**
	 * Atualiza a imagem de um produto e retorna o DTO de resposta.
	 */
	public static ProductResponse updateProductImageResponse(Long catalogId, Long categoryId, Long productId,
			FileUploadDTO imageFile, CatalogDataSource catalogDataSource, ImageDataSource imageDataSource) {
		Product updatedProduct = executeUpdate(catalogId, categoryId, productId, imageFile, catalogDataSource,
				imageDataSource);
		return ProductPresenter.toProductResponse(updatedProduct);
	}

	/**
	 * Executa a atualização de imagem de um produto.
	 */
	private static Product executeUpdate(Long catalogId, Long categoryId, Long productId, FileUploadDTO imageFile,
			CatalogDataSource catalogDataSource, ImageDataSource imageDataSource) {

		log.debug("Atualizando imagem do produto ID: {}", productId);

		CatalogGateway catalogGateway = new CatalogGateway(catalogDataSource);
		ImageStorageGateway imageStorageGateway = new ImageStorageGateway(imageDataSource);

		var catalog = UpdateProductImageInCategoryUseCase.updateProductImageInCategory(catalogId, categoryId, productId,
				imageFile, catalogGateway, imageStorageGateway);

		var savedProduct = catalogGateway.save(catalog);

		return savedProduct.getProductById(productId);
	}
}
