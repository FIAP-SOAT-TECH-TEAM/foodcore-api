package com.soat.fiap.food.core.api.user.infrastructure.in.web.api.controller;

import java.util.List;

import com.soat.fiap.food.core.api.user.core.interfaceadapters.bff.controller.web.api.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import com.soat.fiap.food.core.api.shared.infrastructure.common.source.SecuritySource;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.TokenSource;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.request.LoginRequest;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.request.UserRequest;
import com.soat.fiap.food.core.api.user.infrastructure.in.web.api.dto.response.UserResponse;

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

/**
 * Controlador REST para gerenciamento de usuários
 */
@RestController @RequestMapping("/users") @Tag(name = "Usuários", description = "API para gerenciamento de usuários")
public class UserController {

	private final UserDataSource userDataSource;
	private final TokenSource tokenSource;
	private final SecuritySource securitySource;

	public UserController(UserDataSource userDataSource, TokenSource tokenSource, SecuritySource securitySource) {
		this.userDataSource = userDataSource;
		this.tokenSource = tokenSource;
		this.securitySource = securitySource;
	}

	/**
	 * Lista todos os usuários
	 *
	 * @return Lista de usuários
	 */
	@GetMapping
	@Operation(summary = "Listar todos os usuários", description = "Retorna uma lista com todos os usuários cadastrados", security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponse(responseCode = "200", description = "Lista de usuários retornada com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, array = @ArraySchema(schema = @Schema(implementation = UserResponse.class))))
	@Transactional(readOnly = true)
	public ResponseEntity<List<UserResponse>> getAllUsers() {
		var users = GetAllUsersController.getAllUsers(userDataSource);
		return ResponseEntity.ok(users);
	}

	/**
	 * Busca um usuário por ID
	 *
	 * @param id
	 *            ID do usuário
	 * @return Usuário encontrado ou 404
	 */
	@GetMapping("/{id}")
	@Operation(summary = "Buscar usuário por ID", description = "Retorna um usuário específico pelo seu ID", security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)})
	@Transactional(readOnly = true)
	public ResponseEntity<UserResponse> getUserById(
			@Parameter(description = "ID do usuário", example = "1", required = true) @PathVariable Long id) {

		var response = GetUserByIdController.getUserById(id, userDataSource);
		return ResponseEntity.ok(response);
	}

	/**
	 * Busca um usuário por DOCUMENT
	 *
	 * @param document
	 *            DOCUMENT do usuário
	 * @return Usuário encontrado ou 404
	 */
	@GetMapping("/document/{document}")
	@Operation(summary = "Buscar usuário por DOCUMENT", description = "Retorna um usuário específico pelo seu DOCUMENT", security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuário encontrado", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)})
	@Transactional(readOnly = true)
	public ResponseEntity<UserResponse> getUserByDocument(
			@Parameter(description = "DOCUMENT do usuário", example = "123.456.789-00", required = true)
			@PathVariable String document) {
		var response = GetUserByDocumentController.getUserByDocument(document, userDataSource);
		return ResponseEntity.ok(response);
	}

	/**
	 * Cria um novo usuário
	 *
	 * @param request
	 *            Dados do usuário
	 * @return Usuário criado
	 */
	@PostMapping
	@Operation(summary = "Criar novo usuário", description = "Cria um novo usuário com os dados fornecidos")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "Usuário criado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)})
	@Transactional
	public ResponseEntity<UserResponse> createUser(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do usuário a ser criado", required = true, content = @Content(schema = @Schema(implementation = UserRequest.class)))
			@Valid @RequestBody UserRequest request) {
		var user = SaveUserController.saveUser(request, userDataSource, tokenSource, securitySource);
		return new ResponseEntity<>(user, HttpStatus.CREATED);
	}

	@PostMapping("/login")
	@Operation(summary = "Login", description = "Realiza o login de um usuário com email e senha")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Login realizado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content)})
	@Transactional
	public ResponseEntity<UserResponse> login(
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do login", required = true, content = @Content(schema = @Schema(implementation = LoginRequest.class)))
			@Valid @RequestBody LoginRequest request) {
		var loggedUser = LoginController.login(request.getEmail(), request.getPassword(), userDataSource, tokenSource,
				securitySource);
		return new ResponseEntity<>(loggedUser, HttpStatus.OK);
	}

	/**
	 * Atualiza um usuário existente
	 *
	 * @param id
	 *            ID do usuário
	 * @param request
	 *            Dados atualizados
	 * @return Usuário atualizado ou 404
	 */
	@PutMapping("/{id}")
	@Operation(summary = "Atualizar usuário", description = "Atualiza os dados de um usuário existente", security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "Usuário atualizado com sucesso", content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE, schema = @Schema(implementation = UserResponse.class))),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)})
	@Transactional
	public ResponseEntity<UserResponse> updateUser(
			@Parameter(description = "ID do usuário", example = "1", required = true) @PathVariable Long id,
			@io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do usuário", required = true, content = @Content(schema = @Schema(implementation = UserRequest.class)))
			@Valid @RequestBody UserRequest request) {
		var response = UpdateUserController.updateUser(id, request, userDataSource, securitySource);
		return ResponseEntity.ok(response);
	}

	/**
	 * Remove um usuário
	 *
	 * @param id
	 *            ID do usuário
	 * @return 204 sem conteúdo ou 404
	 */
	@DeleteMapping("/{id}")
	@Operation(summary = "Remover usuário", description = "Remove um usuário pelo seu ID", security = @SecurityRequirement(name = "bearer-key"))
	@ApiResponses(value = {
			@ApiResponse(responseCode = "204", description = "Usuário removido com sucesso", content = @Content),
			@ApiResponse(responseCode = "404", description = "Usuário não encontrado", content = @Content)})
	@Transactional
	public ResponseEntity<Void> deleteUser(
			@Parameter(description = "ID do usuário", example = "1", required = true) @PathVariable Long id) {
		DeleteUserController.deleteUser(id, userDataSource);
		return ResponseEntity.noContent().build();
	}
}
