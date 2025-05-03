package com.soat.fiap.food.core.api.product.infrastructure.adapters.in.controller;

import com.soat.fiap.food.core.api.product.application.services.ProductService;
import com.soat.fiap.food.core.api.product.domain.model.Product;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.in.dto.request.ProductRequest;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.in.dto.response.ProductResponse;
import com.soat.fiap.food.core.api.shared.infrastructure.storage.ImageStorageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Controlador REST para gerenciamento de produtos
 */
@RestController
@RequestMapping("/products")
@Slf4j
@Tag(name = "Produtos", description = "API para gerenciamento de produtos")
public class ProductController {

    private final ProductService productService;
    private final ImageStorageService imageStorageService;

    public ProductController(
            ProductService productService,
            ImageStorageService imageStorageService) {
        this.productService = productService;
        this.imageStorageService = imageStorageService;
    }

    /**
     * Lista todos os produtos
     * @return Lista de produtos
     */
    @GetMapping
    @Operation(summary = "Listar todos os produtos", description = "Retorna uma lista com todos os produtos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class))))
    public ResponseEntity<List<ProductResponse>> getAllProducts() {
        log.debug("Requisição para listar todos os produtos");
        List<Product> products = productService.getAllProducts();
        return ResponseEntity.ok(productService.toResponseList(products));
    }

    /**
     * Busca um produto por ID
     * @param id ID do produto
     * @return Produto encontrado ou 404
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar produto por ID", description = "Retorna um produto específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content)
    })
    public ResponseEntity<ProductResponse> getProductById(
            @Parameter(description = "ID do produto", example = "1", required = true)
            @PathVariable Long id) {
        log.debug("Requisição para buscar produto por ID: {}", id);
        return productService.getProductById(id)
                .map(product -> ResponseEntity.ok(productService.toResponse(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Lista produtos por categoria
     * @param categoryId ID da categoria
     * @return Lista de produtos da categoria
     */
    @GetMapping("/by-category/{categoryId}")
    @Operation(summary = "Listar produtos por categoria", 
               description = "Retorna uma lista com todos os produtos de uma categoria específica")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ProductResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                    content = @Content)
    })
    public ResponseEntity<List<ProductResponse>> getProductsByCategory(
            @Parameter(description = "ID da categoria", example = "1", required = true)
            @PathVariable Long categoryId) {
        log.debug("Requisição para listar produtos da categoria ID: {}", categoryId);
        List<Product> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(productService.toResponseList(products));
    }

    /**
     * Cria um produto
     * @param request Dados do produto
     * @return Produto criado
     */
    @PostMapping
    @Operation(summary = "Criar novo produto", description = "Cria um novo produto com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content)
    })
    public ResponseEntity<ProductResponse> createProduct(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do produto a ser criado", required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequest.class)))
            @Valid @RequestBody ProductRequest request) {
        log.debug("Requisição para criar produto");
        Product product = productService.createProductFromRequest(request);
        return new ResponseEntity<>(productService.toResponse(product), HttpStatus.CREATED);
    }

    /**
     * Atualiza um produto existente
     * @param id ID do produto
     * @param request Dados atualizados
     * @return Produto atualizado ou 404
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content)
    })
    public ResponseEntity<ProductResponse> updateProduct(
            @Parameter(description = "ID do produto", example = "1", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do produto", required = true,
                    content = @Content(schema = @Schema(implementation = ProductRequest.class)))
            @Valid @RequestBody ProductRequest request) {
        
        log.debug("Requisição para atualizar produto ID: {}", id);
        return productService.updateProductFromRequest(id, request)
                .map(product -> ResponseEntity.ok(productService.toResponse(product)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove um produto
     * @param id ID do produto
     * @return 204 sem conteúdo ou 404
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover produto", description = "Remove um produto pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto removido com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "ID do produto", example = "1", required = true)
            @PathVariable Long id) {
        log.debug("Requisição para remover produto ID: {}", id);
        return productService.getProductById(id)
                .map(product -> {
                    productService.deleteProduct(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Faz upload de uma imagem para um produto
     * @param id ID do produto
     * @param image Arquivo de imagem
     * @return Produto atualizado
     */
    @PostMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ProductResponse> uploadProductImage(
            @PathVariable Long id,
            @RequestParam("image") MultipartFile image) {
        
        log.debug("Requisição para fazer upload de imagem para produto ID: {}", id);
        return productService.getProductById(id)
                .map(product -> {
                    if (product.getImageUrl() != null && !product.getImageUrl().isEmpty()) {
                        imageStorageService.deleteImage(product.getImageUrl());
                    }
                    
                    String imagePath = imageStorageService.uploadImage(image, "products/" + id);
                    product.setImageUrl(imagePath);
                    
                    Product updatedProduct = productService.updateProduct(id, product);
                    return ResponseEntity.ok(productService.toResponse(updatedProduct));
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 