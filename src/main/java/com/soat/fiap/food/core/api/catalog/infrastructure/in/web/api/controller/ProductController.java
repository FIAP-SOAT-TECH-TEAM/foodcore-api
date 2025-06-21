package com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.controller;

import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.controller.web.api.product.*;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.requests.ProductRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.in.web.api.dto.responses.ProductResponse;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.ImageDataSource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/catalogs")
@Slf4j
public class ProductController {

    private final CatalogDataSource catalogDataSource;
    private final ImageDataSource imageDataSource;
    private final EventPublisherSource eventPublisherSource;

    public ProductController(CatalogDataSource catalogDataSource, ImageDataSource imageDataSource, EventPublisherSource eventPublisherSource) {
        this.catalogDataSource = catalogDataSource;
        this.imageDataSource = imageDataSource;
        this.eventPublisherSource = eventPublisherSource;
    }

    @PostMapping("/{catalogId}/categories/products")
    @Operation(
            summary = "Criar novo produto",
            description = "Cria um novo produto vinculado a uma categoria existente",
            security = @SecurityRequirement(name = "bearer-key"),
            tags = { "Produtos" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Catálogo ou categoria não encontrada", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Produto com nome já existente na categoria", content = @Content)
    })
    @Tag(name = "Produtos", description = "Operações para gerenciamento de produtos")
    @Transactional
    public ResponseEntity<ProductResponse> createProduct(
            @Parameter(description = "ID do catálogo", example = "1", required = true)
            @PathVariable Long catalogId,
            @Valid @RequestBody ProductRequest request) {
        log.debug("Requisição para criar novo produto na categoria {} do catálogo {}", request.getCategoryId(), catalogId);
        ProductResponse response = SaveProductController.saveProduct(catalogId, request, catalogDataSource, eventPublisherSource);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{catalogId}/categories/{categoryId}/products")
    @Operation(
            summary = "Listar produtos por categoria",
            description = "Retorna todos os produtos de uma determinada categoria dentro de um catálogo",
            security = @SecurityRequirement(name = "bearer-key"),
            tags = { "Produtos" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Catálogo ou categoria não encontrada", content = @Content)
    })
    @Tag(name = "Produtos", description = "Operações para gerenciamento de produtos")
    @Transactional(readOnly = true)
    public ResponseEntity<List<ProductResponse>> getAllProductsByCategory(
            @Parameter(description = "ID do catálogo", example = "1", required = true)
            @PathVariable Long catalogId,
            @Parameter(description = "ID da categoria", example = "5", required = true)
            @PathVariable Long categoryId
    ) {
        log.debug("Requisição para listar produtos da categoria {} no catálogo {}", categoryId, catalogId);
        List<ProductResponse> products = GetAllProductsController.getAllProducts(catalogId, categoryId, catalogDataSource);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{catalogId}/categories/{categoryId}/products/{productId}")
    @Operation(
            summary = "Buscar produto por ID",
            description = "Retorna um produto específico de uma categoria pelo ID do produto",
            security = @SecurityRequirement(name = "bearer-key"),
            tags = { "Produtos" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Catálogo, categoria ou produto não encontrado", content = @Content)
    })
    @Tag(name = "Produtos", description = "Operações para gerenciamento de produtos")
    @Transactional(readOnly = true)
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "ID do catálogo", example = "1", required = true)
            @PathVariable Long catalogId,
            @Parameter(description = "ID da categoria", example = "10", required = true)
            @PathVariable Long categoryId,
            @Parameter(description = "ID do produto", example = "100", required = true)
            @PathVariable Long productId) {
        log.debug("Requisição para buscar produto {} na categoria {} do catálogo {}", productId, categoryId, catalogId);
        ProductResponse response = GetProductByIdController.getProductById(catalogId, categoryId, productId, catalogDataSource);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{catalogId}/categories/{categoryId}/products/{productId}")
    @Operation(
            summary = "Atualizar produto",
            description = "Atualiza os dados de um produto existente",
            security = @SecurityRequirement(name = "bearer-key"),
            tags = { "Produtos" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Catálogo, categoria ou produto não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Produto com nome já existente na categoria", content = @Content)
    })
    @Tag(name = "Produtos", description = "Operações para gerenciamento de produtos")
    @Transactional
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "ID do catálogo", example = "1", required = true)
            @PathVariable Long catalogId,
            @Parameter(description = "ID atual da categoria do produto", example = "10", required = true)
            @PathVariable Long categoryId,
            @Parameter(description = "ID do produto", example = "100", required = true)
            @PathVariable Long productId,
            @Valid @RequestBody ProductRequest request) {
        log.debug("Requisição para atualizar produto {} na categoria {} do catálogo {}", productId, categoryId, catalogId);
        ProductResponse response = UpdateProductController.updateProduct(catalogId, categoryId, productId, request, catalogDataSource);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{catalogId}/categories/{categoryId}/products/{productId}")
    @Operation(
            summary = "Excluir produto",
            description = "Exclui um produto específico de uma categoria",
            security = @SecurityRequirement(name = "bearer-key"),
            tags = { "Produtos" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto excluído com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Catálogo, categoria ou produto não encontrado", content = @Content)
    })
    @Tag(name = "Produtos", description = "Operações para gerenciamento de produtos")
    @Transactional
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID do catálogo", example = "1", required = true)
            @PathVariable Long catalogId,
            @Parameter(description = "ID da categoria", example = "10", required = true)
            @PathVariable Long categoryId,
            @Parameter(description = "ID do produto", example = "100", required = true)
            @PathVariable Long productId) {
        log.debug("Requisição para excluir produto {} da categoria {} do catálogo {}", productId, categoryId, catalogId);
        DeleteProductController.deleteProduct(catalogId, categoryId, productId, catalogDataSource);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{catalogId}/categories/{categoryId}/products/{productId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Atualizar imagem do produto",
            description = "Atualiza apenas a imagem de um produto existente",
            security = @SecurityRequirement(name = "bearer-key"),
            tags = { "Produtos" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imagem do produto atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Catálogo, categoria ou produto não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Imagem inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro ao processar imagem", content = @Content)
    })
    @Tag(name = "Produtos", description = "Operações para gerenciamento de produtos")
    @Transactional
    public ResponseEntity<Void> updateProductImage(
            @Parameter(description = "ID do catálogo", example = "1", required = true)
            @PathVariable Long catalogId,
            @Parameter(description = "ID da categoria do produto", example = "10", required = true)
            @PathVariable Long categoryId,
            @Parameter(description = "ID do produto", example = "100", required = true)
            @PathVariable Long productId,
            @Parameter(description = "Arquivo da nova imagem", required = true)
            @RequestPart("imageFile") MultipartFile imageFile
    ) {
        log.debug("Requisição para atualizar imagem do produto {} na categoria {} do catálogo {}", productId, categoryId, catalogId);
        UpdateProductImageController.updateProductImage(catalogId, categoryId, productId, imageFile, catalogDataSource, imageDataSource);
        return ResponseEntity.noContent().build();
    }

}