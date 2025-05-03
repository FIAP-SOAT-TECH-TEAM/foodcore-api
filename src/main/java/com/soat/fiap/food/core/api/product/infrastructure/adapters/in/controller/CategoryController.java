package com.soat.fiap.food.core.api.product.infrastructure.adapters.in.controller;

import com.soat.fiap.food.core.api.product.application.ports.in.CategoryUseCase;
import com.soat.fiap.food.core.api.product.domain.model.Category;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.in.dto.request.CategoryRequest;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.in.dto.response.CategoryResponse;
import com.soat.fiap.food.core.api.product.mapper.CategoryDtoMapper;
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

/**
 * Controlador REST para gerenciamento de categorias
 */
@RestController
@RequestMapping("/api/categories")
@Tag(name = "Categorias", description = "API para gerenciamento de categorias de produtos")
public class CategoryController {

    private final CategoryUseCase categoryUseCase;
    private final CategoryDtoMapper categoryDtoMapper;

    public CategoryController(CategoryUseCase categoryUseCase, CategoryDtoMapper categoryDtoMapper) {
        this.categoryUseCase = categoryUseCase;
        this.categoryDtoMapper = categoryDtoMapper;
    }

    /**
     * Lista todas as categorias
     * @return Lista de categorias
     */
    @GetMapping
    @Operation(summary = "Listar todas as categorias", description = "Retorna uma lista com todas as categorias cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de categorias retornada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = CategoryResponse.class))))
    public ResponseEntity<List<CategoryResponse>> getAllCategories() {
        List<Category> categories = categoryUseCase.getAllCategories();
        return ResponseEntity.ok(categoryDtoMapper.toResponseList(categories));
    }

    /**
     * Busca uma categoria por ID
     * @param id ID da categoria
     * @return Categoria encontrada ou 404
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar categoria por ID", description = "Retorna uma categoria específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                    content = @Content)
    })
    public ResponseEntity<CategoryResponse> getCategoryById(
            @Parameter(description = "ID da categoria", example = "1", required = true)
            @PathVariable Long id) {
        return categoryUseCase.getCategoryById(id)
                .map(category -> ResponseEntity.ok(categoryDtoMapper.toResponse(category)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria uma categoria
     * @param request Dados da categoria
     * @return Categoria criada
     */
    @PostMapping
    @Operation(summary = "Criar nova categoria", description = "Cria uma nova categoria com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content)
    })
    public ResponseEntity<CategoryResponse> createCategory(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da categoria a ser criada", required = true,
                    content = @Content(schema = @Schema(implementation = CategoryRequest.class)))
            @Valid @RequestBody CategoryRequest request) {
        Category category = categoryDtoMapper.toDomain(request);
        Category createdCategory = categoryUseCase.createCategory(category);
        return new ResponseEntity<>(categoryDtoMapper.toResponse(createdCategory), HttpStatus.CREATED);
    }

    /**
     * Atualiza uma categoria existente
     * @param id ID da categoria
     * @param request Dados atualizados
     * @return Categoria atualizada ou 404
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CategoryResponse.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                    content = @Content)
    })
    public ResponseEntity<CategoryResponse> updateCategory(
            @Parameter(description = "ID da categoria", example = "1", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados da categoria", required = true,
                    content = @Content(schema = @Schema(implementation = CategoryRequest.class)))
            @Valid @RequestBody CategoryRequest request) {
        
        return categoryUseCase.getCategoryById(id)
                .map(existingCategory -> {
                    categoryDtoMapper.updateDomainFromRequest(request, existingCategory);
                    Category updatedCategory = categoryUseCase.updateCategory(id, existingCategory);
                    return ResponseEntity.ok(categoryDtoMapper.toResponse(updatedCategory));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove uma categoria
     * @param id ID da categoria
     * @return 204 sem conteúdo ou 404
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover categoria", description = "Remove uma categoria pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID da categoria", example = "1", required = true)
            @PathVariable Long id) {
        return categoryUseCase.getCategoryById(id)
                .map(category -> {
                    categoryUseCase.deleteCategory(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 