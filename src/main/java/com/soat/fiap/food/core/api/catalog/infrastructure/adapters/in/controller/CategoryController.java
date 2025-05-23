//package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.in.controller;
//
//import com.soat.fiap.food.core.api.catalog.application.services.CategoryService;
//import com.soat.fiap.food.core.api.catalog.domain.model.Category;
//import com.soat.fiap.food.core.api.catalog.application.dto.request.CategoryRequest;
//import com.soat.fiap.food.core.api.catalog.application.dto.response.CategoryResponse;
//import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
//import io.swagger.v3.oas.annotations.Operation;
//import io.swagger.v3.oas.annotations.Parameter;
//import io.swagger.v3.oas.annotations.media.ArraySchema;
//import io.swagger.v3.oas.annotations.media.Content;
//import io.swagger.v3.oas.annotations.media.Schema;
//import io.swagger.v3.oas.annotations.responses.ApiResponse;
//import io.swagger.v3.oas.annotations.responses.ApiResponses;
//import io.swagger.v3.oas.annotations.tags.Tag;
//import jakarta.validation.Valid;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.MediaType;
//import org.springframework.http.ResponseEntity;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//
///**
// * Controlador REST para gerenciamento de categorias
// */
//@RestController
//@RequestMapping("/api/categories")
//@Tag(name = "Categorias", description = "API para gerenciamento de categorias de produtos")
//public class CategoryController {
//
//    private final CategoryService categoryService;
//    private final CustomLogger logger;
//
//    public CategoryController(CategoryService categoryService) {
//        this.categoryService = categoryService;
//        this.logger = CustomLogger.getLogger(getClass());
//    }
//
//    /**
//     * Lista todas as categorias
//     * @return Lista de categorias
//     */
//    @GetMapping
//    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista com todas as categorias cadastradas")
//    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso",
//            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                    array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))))
//    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
//        logger.debug("Requisição para listar todas as categorias");
//        List<Category> categories = categoryService.getAllCategories();
//        return ResponseEntity.ok(categoryService.toResponseList(categories));
//    }
//
//    /**
//     * Busca uma categoria por ID
//     * @param id ID da categoria
//     * @return Categoria encontrada ou 404
//     */
//    @GetMapping("/{id}")
//    @Operation(summary = "Buscar categoria por ID", description = "Retorna uma categoria específica pelo seu ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Categoria encontrada",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = CategoryResponse.class))),
//            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
//                    content = @Content)
//    })
//    public ResponseEntity<CategoryResponse> getCategoryById(
//            @Parameter(description = "ID da categoria", example = "1", required = true)
//            @PathVariable Long id) {
//        logger.debug("Requisição para buscar categoria por ID: {}", id);
//        return categoryService.getCategoryById(id)
//                .map(category -> ResponseEntity.ok(categoryService.toResponse(category)))
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    /**
//     * Cria uma categoria com ou sem imagem
//     * @param name Nome da categoria
//     * @param description Descrição da categoria
//     * @param displayOrder Ordem de exibição
//     * @param image Arquivo de imagem (opcional)
//     * @return Categoria criada
//     */
//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(
//        summary = "Criar nova categoria",
//        description = "Cria uma nova categoria com os dados fornecidos e opcionalmente com uma imagem"
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = CategoryResponse.class))),
//            @ApiResponse(responseCode = "400", description = "Dados inválidos",
//                    content = @Content)
//    })
//    @Transactional
//    public ResponseEntity<CategoryResponse> createCategory(
//            @Parameter(description = "Nome da categoria", example = "Lanches", required = true)
//            @RequestParam("name") String name,
//
//            @Parameter(description = "Descrição da categoria", example = "Hambúrgueres e sanduíches")
//            @RequestParam(value = "description", required = false) String description,
//
//            @Parameter(description = "Ordem de exibição da categoria", example = "1")
//            @RequestParam(value = "displayOrder", required = false) Integer displayOrder,
//
//            @Parameter(description = "Imagem da categoria (opcional)")
//            @RequestParam(value = "image", required = false) MultipartFile image) {
//
//        logger.info("Requisição para criar categoria: name={}", name);
//
//        try {
//            CategoryRequest categoryRequest = new CategoryRequest();
//            categoryRequest.setName(name);
//            categoryRequest.setDescription(description);
//            categoryRequest.setDisplayOrder(displayOrder);
//
//            Category category = categoryService.createCategoryWithImage(categoryRequest, image);
//
//            return new ResponseEntity<>(categoryService.toResponse(category), HttpStatus.CREATED);
//        } catch (Exception e) {
//            logger.error("Erro ao processar a requisição de criação de categoria: {}", e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    /**
//     * Atualiza uma categoria existente
//     * @param id ID da categoria
//     * @param request Dados atualizados
//     * @return Categoria atualizada ou 404
//     */
//    @PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE)
//    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria existente")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = CategoryResponse.class))),
//            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
//                    content = @Content)
//    })
//    public ResponseEntity<CategoryResponse> updateCategory(
//            @Parameter(description = "ID da categoria", example = "1", required = true)
//            @PathVariable Long id,
//            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados da categoria", required = true,
//                    content = @Content(schema = @Schema(implementation = CategoryRequest.class)))
//            @Valid @RequestBody CategoryRequest request) {
//
//        logger.debug("Requisição para atualizar categoria ID: {}", id);
//
//        try {
//            return categoryService.updateCategoryFromRequest(id, request)
//                    .map(category -> ResponseEntity.ok(categoryService.toResponse(category)))
//                    .orElse(ResponseEntity.notFound().build());
//        } catch (Exception e) {
//            logger.error("Erro ao processar atualização de categoria: {}", e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    /**
//     * Remove uma categoria
//     * @param id ID da categoria
//     * @return 204 sem conteúdo ou 404
//     */
//    @DeleteMapping("/{id}")
//    @Operation(summary = "Remover categoria", description = "Remove uma categoria pelo seu ID")
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso",
//                    content = @Content),
//            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
//                    content = @Content)
//    })
//    public ResponseEntity<Void> deleteCategory(
//            @Parameter(description = "ID da categoria", example = "1", required = true)
//            @PathVariable Long id) {
//        logger.debug("Requisição para remover categoria ID: {}", id);
//        return categoryService.getCategoryById(id)
//                .map(category -> {
//                    categoryService.deleteCategory(id);
//                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    /**
//     * Atualiza apenas a imagem de uma categoria existente
//     * @param id ID da categoria
//     * @param image Arquivo de imagem
//     * @return Categoria atualizada
//     */
//    @PatchMapping(value = "/{id}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @Operation(
//        summary = "Atualizar imagem da categoria",
//        description = "Atualiza apenas a imagem de uma categoria existente"
//    )
//    @ApiResponses(value = {
//            @ApiResponse(responseCode = "200", description = "Imagem atualizada com sucesso",
//                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                            schema = @Schema(implementation = CategoryResponse.class))),
//            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
//                    content = @Content)
//    })
//    @Transactional
//    public ResponseEntity<CategoryResponse> updateCategoryImage(
//            @Parameter(description = "ID da categoria", example = "1", required = true)
//            @PathVariable Long id,
//
//            @Parameter(description = "Arquivo da nova imagem", required = true)
//            @RequestParam("image") MultipartFile image) {
//
//        logger.info("Requisição para atualizar imagem da categoria ID: {}", id);
//
//        try {
//            Category category = categoryService.updateCategoryImage(id, image);
//            return ResponseEntity.ok(categoryService.toResponse(category));
//        } catch (RuntimeException e) {
//            if (e.getMessage().contains("não encontrada")) {
//                return ResponseEntity.notFound().build();
//            }
//            throw e;
//        }
//    }
//}