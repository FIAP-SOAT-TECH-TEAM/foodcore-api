package com.soat.fiap.food.core.api.user.infrastructure.adapters.in.controller;

import com.soat.fiap.food.core.api.user.application.ports.in.RoleUseCase;
import com.soat.fiap.food.core.api.user.application.dto.request.RoleRequest;
import com.soat.fiap.food.core.api.user.application.dto.response.RoleResponse;
import com.soat.fiap.food.core.api.user.application.mapper.RoleDtoMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciamento de roles
 */
@RestController
@RequestMapping("/roles")
@Tag(name = "roles", description = "API para gerenciamento das roles")
public class RoleController {

    private final RoleUseCase roleUseCase;
    private final RoleDtoMapper roleDtoMapper;

    public RoleController(RoleUseCase roleUseCase, RoleDtoMapper roleDtoMapper) {
        this.roleUseCase = roleUseCase;
        this.roleDtoMapper = roleDtoMapper;
    }
    /**
     * Lista todos oas roles
     * @return Lista de roles
     */
    @GetMapping
    @Operation(summary = "Listar todos as roles", description = "Retorna uma lista com todos as roles cadastradas")
    @ApiResponse(responseCode = "200", description = "Lista de roles retornada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = RoleResponse.class))))
    public ResponseEntity<List<RoleResponse>> getAllRoles() {
        //TODO: Implementar a lógica de listagem de roles
        return ResponseEntity.ok(List.of());
//        List<Role> roles = roleUseCase.getAllRoles();
//        return ResponseEntity.ok(roleDtoMapper.toResponseList(roles));
    }

    /**
     * Busca uma role por ID
     * @param id ID da role
     * @return Role encontrada ou 404
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar role por ID", description = "Retorna uma role específica pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role encontrada",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RoleResponse.class))),
            @ApiResponse(responseCode = "404", description = "Role não encontrada",
                    content = @Content)
    })
    public ResponseEntity<RoleResponse> getRoleById(
            @Parameter(description = "ID da role", example = "1", required = true)
            @PathVariable Long id) {
        //TODO: Implementar a lógica de busca de roles
        return ResponseEntity.ok(new RoleResponse());
//        return userUseCase.getUserById(id)
//                .map(user -> ResponseEntity.ok(userDtoMapper.toResponse(user)))
//                .orElse(ResponseEntity.notFound().build());
    }


    /**
     * Cria um nova role
     * @param request Dados da role
     * @return Role criada
     */
    @PostMapping
    @Operation(summary = "Criar nova role", description = "Cria uma nova role com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Role criado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RoleResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content)
    })
    public ResponseEntity<RoleResponse> createRole(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados da role a ser criada", required = true,
                    content = @Content(schema = @Schema(implementation = RoleRequest.class)))
            @Valid @RequestBody RoleRequest request) {

        //TODO: Implementar a lógica de criação de roles
        return ResponseEntity.ok(new RoleResponse());
//        try {
//            User user = userDtoMapper.toDomain(request);
//            User createdUser = userUseCase.createUser(user);
//            return new ResponseEntity<>(userDtoMapper.toResponse(createdUser), HttpStatus.CREATED);
//        } catch (IllegalArgumentException e) {
//            return ResponseEntity.badRequest().build();
//        }
    }

    /**
     * Atualiza uma role existente
     * @param id ID da role
     * @param request Dados atualizados
     * @return Role atualizada ou 404
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar role", description = "Atualiza os dados de uma role existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Role atualizada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RoleResponse.class))),
            @ApiResponse(responseCode = "404", description = "Role não encontrado",
                    content = @Content)
    })
    public ResponseEntity<RoleResponse> updateRole(
            @Parameter(description = "ID da role", example = "1", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados da role", required = true,
                    content = @Content(schema = @Schema(implementation = RoleRequest.class)))
            @Valid @RequestBody RoleRequest request) {

        //TODO: Implementar a lógica de atualização de roles
        return ResponseEntity.ok(new RoleResponse());

//        return userUseCase.getUserById(id)
//                .map(existingUser -> {
//                    userDtoMapper.updateDomainFromRequest(request, existingUser);
//                    User updatedUser = userUseCase.updateUser(id, existingUser);
//                    return ResponseEntity.ok(userDtoMapper.toResponse(updatedUser));
//                })
//                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove uma role
     * @param id ID da role a ser removida
     * @return 204 sem conteúdo ou 404
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover role", description = "Remove uma role pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Role removida com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Role não encontrada",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteRole(
            @Parameter(description = "ID do role", example = "1", required = true)
            @PathVariable Long id) {

        //TODO: Implementar a lógica de deleção de roles
        return ResponseEntity.noContent().build();

    }
}