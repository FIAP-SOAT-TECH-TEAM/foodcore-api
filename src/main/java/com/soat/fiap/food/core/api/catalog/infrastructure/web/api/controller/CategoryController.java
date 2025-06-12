package com.soat.fiap.food.core.api.catalog.infrastructure.web.api.controller;

import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.requests.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.web.api.dto.responses.CategoryResponse;
import com.soat.fiap.food.core.api.catalog.application.ports.in.CatalogUseCase;
import com.soat.fiap.food.core.api.catalog.application.services.CatalogService;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/catalogs")
public class CategoryController {

    private final CatalogUseCase catalogUseCase;
    private final CustomLogger logger;

    public CategoryController(CatalogService catalogService) {
        this.catalogUseCase = catalogService;
        this.logger = CustomLogger.getLogger(getClass());
    }

    @PostMapping("/categories")
    @Operation(
            summary = "Criar nova categoria",
            description = "Cria uma nova categoria vinculada a um catálogo existente",
            security = @SecurityRequirement(name = "bearer-key"),
            tags = { "Categorias" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Categoria com nome já existente no catálogo", content = @Content)
    })
    @Tag(name = "Categorias", description = "Operações para gerenciamento de categorias de produtos")
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest request) {
        logger.debug("Requisição para criar nova categoria no catálogo: {}", request.getCatalogId());
        CategoryResponse response = catalogUseCase.saveCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{catalogId}/categories")
    @Operation(
            summary = "Listar categorias do catálogo",
            description = "Retorna todas as categorias associadas a um catálogo",
            security = @SecurityRequirement(name = "bearer-key"),
            tags = { "Categorias" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class)))),
            @ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content)
    })
    @Tag(name = "Categorias", description = "Operações para gerenciamento de categorias de produtos")
    public ResponseEntity<List<CategoryResponse>> getAllCategories(
            @Parameter(description = "ID do catálogo", example = "1", required = true)
            @PathVariable Long catalogId) {
        List<CategoryResponse> responseList = catalogUseCase.getAllCategories(catalogId);
        return ResponseEntity.ok(responseList);
    }

    @GetMapping("/{catalogId}/categories/{categoryId}")
    @Operation(
            summary = "Buscar categoria por ID",
            description = "Retorna uma categoria específica de um catálogo pelo ID da categoria",
            security = @SecurityRequirement(name = "bearer-key"),
            tags = { "Categorias" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria ou catálogo não encontrado", content = @Content)
    })
    @Tag(name = "Categorias", description = "Operações para gerenciamento de categorias de produtos")
    public ResponseEntity<CategoryResponse> getCategoryById(
            @Parameter(description = "ID do catálogo", example = "1", required = true)
            @PathVariable Long catalogId,
            @Parameter(description = "ID da categoria", example = "10", required = true)
            @PathVariable Long categoryId
    ) {
        CategoryResponse response = catalogUseCase.getCategoryById(catalogId, categoryId);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{catalogId}/categories/{categoryId}")
    @Operation(
            summary = "Atualizar categoria",
            description = "Atualiza uma categoria vinculada a um catálogo existente",
            security = @SecurityRequirement(name = "bearer-key"),
            tags = { "Categorias" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Categoria com nome já existente no catálogo", content = @Content)
    })
    @Tag(name = "Categorias", description = "Operações para gerenciamento de categorias de produtos")
    public ResponseEntity<CategoryResponse> updateCategory(
            @Parameter(description = "ID atual do catálogo da categoria", example = "2", required = true)
            @PathVariable Long catalogId,
            @Parameter(description = "ID da categoria", example = "1", required = true)
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryRequest request) {
        logger.debug("Requisição para atualizar categoria no catálogo: {}", request.getCatalogId());
        CategoryResponse response = catalogUseCase.updateCategory(catalogId, categoryId, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{catalogId}/categories/{categoryId}")
    @Operation(
            summary = "Excluir categoria",
            description = "Exclui uma categoria específica de um catálogo",
            security = @SecurityRequirement(name = "bearer-key"),
            tags = { "Categorias" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria excluída com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria ou catálogo não encontrado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Categoria possui produtos associados", content = @Content)
    })
    @Tag(name = "Categorias", description = "Operações para gerenciamento de categorias de produtos")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID do catálogo", example = "1", required = true)
            @PathVariable Long catalogId,
            @Parameter(description = "ID da categoria", example = "10", required = true)
            @PathVariable Long categoryId
    ) {
        catalogUseCase.deleteCategory(catalogId, categoryId);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping(value = "/{catalogId}/categories/{categoryId}/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(
            summary = "Atualizar imagem do category",
            description = "Atualiza apenas a imagem de um categoryexistente",
            security = @SecurityRequirement(name = "bearer-key"),
            tags = { "Categorias" }
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Imagem da categoria atualizada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Catálogo ou categoria não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Imagem inválida", content = @Content),
            @ApiResponse(responseCode = "500", description = "Erro ao processar imagem", content = @Content)
    })
    @Tag(name = "Categorias", description = "Operações para gerenciamento de categorias de produtos")
    public ResponseEntity<Void> updateCategoryImage(
            @Parameter(description = "ID do catálogo", example = "1", required = true)
            @PathVariable Long catalogId,
            @Parameter(description = "ID da categoria", example = "10", required = true)
                    @PathVariable Long categoryId,
            @Parameter(description = "Arquivo da nova imagem", required = true)
            @RequestPart("imageFile") MultipartFile imageFile
    ) {
        logger.debug("Requisição para atualizar imagem da categoria {} do catálogo {}", categoryId, catalogId);
        catalogUseCase.updateCategoryImage(catalogId, categoryId, imageFile);
        return ResponseEntity.noContent().build();
    }

}