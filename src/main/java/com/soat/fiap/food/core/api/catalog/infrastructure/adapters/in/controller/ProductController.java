package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.in.controller;

import com.soat.fiap.food.core.api.catalog.application.services.ProductService;
import com.soat.fiap.food.core.api.catalog.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.application.dto.request.ProductRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.ProductResponse;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.util.List;

/**
 * Controlador REST para gerenciamento de produtos
 */
@RestController
@RequestMapping("/api/products")
@Tag(name = "Produtos", description = "API para gerenciamento de produtos")
public class ProductController {

    private final ProductService productService;
    private final CustomLogger logger;

    public ProductController(ProductService productService) {
        this.productService = productService;
        this.logger = CustomLogger.getLogger(getClass());
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
        logger.debug("Requisição para listar todos os produtos");
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
        logger.debug("Requisição para buscar produto por ID: {}", id);
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
        logger.debug("Requisição para listar produtos da categoria ID: {}", categoryId);
        List<Product> products = productService.getProductsByCategory(categoryId);
        return ResponseEntity.ok(productService.toResponseList(products));
    }

    /**
     * Cria um produto com ou sem imagem
     * @param name Nome do produto
     * @param description Descrição do produto 
     * @param price Preço do produto
     * @param categoryId ID da categoria
     * @param displayOrder Ordem de exibição
     * @param image Arquivo de imagem (opcional)
     * @return Produto criado
     */
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Criar novo produto", 
        description = "Cria um novo produto com os dados fornecidos e opcionalmente com uma imagem"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content)
    })
    @Transactional
    public ResponseEntity<ProductResponse> createProduct(
            @Parameter(description = "Nome do produto", example = "X-Burger", required = true)
            @RequestParam("name") String name,
            
            @Parameter(description = "Descrição do produto", example = "Hambúrguer com queijo, alface e tomate")
            @RequestParam(value = "description", required = false) String description,
            
            @Parameter(description = "Preço do produto", example = "25.90", required = true)
            @RequestParam("price") BigDecimal price,
            
            @Parameter(description = "ID da categoria do produto", example = "1", required = true)
            @RequestParam("categoryId") Long categoryId,
            
            @Parameter(description = "Ordem de exibição do produto", example = "1")
            @RequestParam(value = "displayOrder", required = false) Integer displayOrder,
            
            @Parameter(description = "Imagem do produto (opcional)")
            @RequestParam(value = "image", required = false) MultipartFile image) {
        
        logger.info("Requisição para criar produto: name={}, categoryId={}", name, categoryId);
        
        try {
            ProductRequest productRequest = new ProductRequest();
            productRequest.setName(name);
            productRequest.setDescription(description);
            productRequest.setPrice(price);
            productRequest.setCategoryId(categoryId);
            productRequest.setDisplayOrder(displayOrder);
            
            Product product = productService.createProductWithImage(productRequest, image);
            
            return new ResponseEntity<>(productService.toResponse(product), HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Erro ao processar a requisição de criação de produto: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Atualiza um produto existente
     * @param id ID do produto
     * @param request Dados atualizados
     * @return Produto atualizado ou 404
     */
    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
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
        
        logger.debug("Requisição para atualizar produto ID: {}", id);
        
        try {
            return productService.updateProductFromRequest(id, request)
                    .map(product -> ResponseEntity.ok(productService.toResponse(product)))
                    .orElse(ResponseEntity.notFound().build());
        } catch (Exception e) {
            logger.error("Erro ao processar atualização de produto: {}", e.getMessage(), e);
            throw e;
        }
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
        logger.debug("Requisição para remover produto ID: {}", id);
        return productService.getProductById(id)
                .map(product -> {
                    productService.deleteProduct(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
    
    /**
     * Atualiza apenas a imagem de um produto existente
     * @param id ID do produto
     * @param image Arquivo de imagem
     * @return Produto atualizado
     */
    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
        summary = "Atualizar imagem do produto", 
        description = "Atualiza apenas a imagem de um produto existente"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Imagem atualizada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ProductResponse.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado",
                    content = @Content)
    })
    @Transactional
    public ResponseEntity<ProductResponse> updateProductImage(
            @Parameter(description = "ID do produto", example = "1", required = true)
            @PathVariable Long id,
            
            @Parameter(description = "Arquivo da nova imagem", required = true)
            @RequestParam("image") MultipartFile image) {
        
        logger.info("Requisição para atualizar imagem do produto ID: {}", id);
        
        try {
            Product product = productService.updateProductImage(id, image);
            return ResponseEntity.ok(productService.toResponse(product));
        } catch (RuntimeException e) {
            if (e.getMessage().contains("não encontrado")) {
                return ResponseEntity.notFound().build();
            }
            throw e;
        }
    }
} 