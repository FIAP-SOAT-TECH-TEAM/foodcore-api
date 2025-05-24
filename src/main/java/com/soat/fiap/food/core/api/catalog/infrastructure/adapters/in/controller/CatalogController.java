package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.in.controller;

import com.soat.fiap.food.core.api.catalog.application.dto.request.CatalogRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.request.CategoryRequest;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CatalogResponse;
import com.soat.fiap.food.core.api.catalog.application.dto.response.CategoryResponse;
import com.soat.fiap.food.core.api.catalog.application.ports.in.CatalogUseCase;
import com.soat.fiap.food.core.api.catalog.application.ports.in.CategoryUseCase;
import com.soat.fiap.food.core.api.catalog.application.services.CatalogService;
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
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/catalogs")
@Tag(name = "Catálogos", description = "API para gerenciamento de catálogos de produtos")
public class CatalogController {

    private final CatalogUseCase catalogUseCase;
    private final CustomLogger logger;

    public CatalogController(CatalogService catalogService) {
        this.catalogUseCase = catalogService;
        this.logger = CustomLogger.getLogger(getClass());
    }

    @GetMapping
    @Operation(summary = "Listar todos os catálogos", description = "Retorna uma lista com todos os catálogos cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de catálogos retornada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = CatalogResponse.class))))
    public ResponseEntity<List<CatalogResponse>> getAllCatalogs() {
        logger.debug("Requisição para listar todos os catálogos");
        return ResponseEntity.ok(catalogUseCase.getAllCatalogs());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar catálogo por ID", description = "Retorna um catálogo específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Catálogo encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogResponse.class))),
            @ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content)
    })
    public ResponseEntity<CatalogResponse> getCatalogById(
            @Parameter(description = "ID do catálogo", example = "1", required = true)
            @PathVariable Long id) {
        logger.debug("Requisição para buscar catálogo por ID: {}", id);
        return ResponseEntity.ok(catalogUseCase.getCatalogById(id));
    }

    @PostMapping
    @Operation(summary = "Criar novo catálogo", description = "Cria um novo catálogo com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Catálogo criado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogResponse.class))),
            @ApiResponse(responseCode = "409", description = "Catálogo com nome já existente", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)
    })
    public ResponseEntity<CatalogResponse> createCatalog(
            @Valid @RequestBody CatalogRequest request) {
        logger.debug("Requisição para criar novo catálogo");
        var response = catalogUseCase.saveCatalog(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar catálogo", description = "Atualiza os dados de um catálogo existente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Catálogo atualizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CatalogResponse.class))),
            @ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Catálogo com nome já existente", content = @Content),
    })
    public ResponseEntity<CatalogResponse> updateCatalog(
            @Parameter(description = "ID do catálogo", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody CatalogRequest request) {
        logger.debug("Requisição para atualizar catálogo: {}", id);
        var response = catalogUseCase.updateCatalog(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Excluir catálogo", description = "Exclui um catálogo pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Catálogo excluído com sucesso", content = @Content),
            @ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content),
            @ApiResponse(responseCode = "409", description = "Catálogo possui categorias associadas", content = @Content)
    })
    public ResponseEntity<Void> deleteCatalog(
            @Parameter(description = "ID do catálogo", example = "1", required = true)
            @PathVariable Long id) {
        logger.debug("Requisição para excluir catálogo: {}", id);
        catalogUseCase.deleteCatalog(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/categories")
    @Operation(summary = "Criar nova categoria", description = "Cria uma nova categoria vinculada a um catálogo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Categoria com nome já existente no catálogo", content = @Content)
    })
    public ResponseEntity<CategoryResponse> createCategory(
            @Valid @RequestBody CategoryRequest request) {
        logger.debug("Requisição para criar nova categoria no catálogo: {}", request.getCatalogId());
        CategoryResponse response = catalogUseCase.saveCategory(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PutMapping("/{catalogId}/categories/{categoryId}")
    @Operation(summary = "Atualizar categoria", description = "Atualiza uma categoria vinculada a um catálogo existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Catálogo não encontrado", content = @Content),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "409", description = "Categoria com nome já existente no catálogo", content = @Content)
    })
    public ResponseEntity<CategoryResponse> updateCategory(
            @Parameter(description = "ID do catálogo", example = "2", required = true)
            @PathVariable Long catalogId,
            @Parameter(description = "ID da categoria", example = "1", required = true)
            @PathVariable Long categoryId,
            @Valid @RequestBody CategoryRequest request) {
        logger.debug("Requisição para atualizar categoria no catálogo: {}", request.getCatalogId());
        CategoryResponse response = catalogUseCase.updateCategory(catalogId, categoryId, request);
        return ResponseEntity.ok(response);
    }
}
