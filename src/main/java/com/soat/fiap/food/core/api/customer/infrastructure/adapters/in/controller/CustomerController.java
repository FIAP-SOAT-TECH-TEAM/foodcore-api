package com.soat.fiap.food.core.api.customer.infrastructure.adapters.in.controller;

import com.soat.fiap.food.core.api.customer.application.ports.in.CustomerUseCase;
import com.soat.fiap.food.core.api.customer.domain.model.Customer;
import com.soat.fiap.food.core.api.customer.infrastructure.adapters.in.dto.request.CustomerRequest;
import com.soat.fiap.food.core.api.customer.infrastructure.adapters.in.dto.response.CustomerResponse;
import com.soat.fiap.food.core.api.customer.mapper.CustomerDtoMapper;
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
 * Controlador REST para gerenciamento de clientes
 */
@RestController
@RequestMapping("/api/customers")
@Tag(name = "Clientes", description = "API para gerenciamento de clientes")
public class CustomerController {

    private final CustomerUseCase customerUseCase;
    private final CustomerDtoMapper customerDtoMapper;

    public CustomerController(CustomerUseCase customerUseCase, CustomerDtoMapper customerDtoMapper) {
        this.customerUseCase = customerUseCase;
        this.customerDtoMapper = customerDtoMapper;
    }

    /**
     * Lista todos os clientes
     * @return Lista de clientes
     */
    @GetMapping
    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista com todos os clientes cadastrados")
    @ApiResponse(responseCode = "200", description = "Lista de clientes retornada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = CustomerResponse.class))))
    public ResponseEntity<List<CustomerResponse>> getAllCustomers() {
        List<Customer> customers = customerUseCase.getAllCustomers();
        return ResponseEntity.ok(customerDtoMapper.toResponseList(customers));
    }

    /**
     * Busca um cliente por ID
     * @param id ID do cliente
     * @return Cliente encontrado ou 404
     */
    @GetMapping("/{id}")
    @Operation(summary = "Buscar cliente por ID", description = "Retorna um cliente específico pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content)
    })
    public ResponseEntity<CustomerResponse> getCustomerById(
            @Parameter(description = "ID do cliente", example = "1", required = true)
            @PathVariable Long id) {
        return customerUseCase.getCustomerById(id)
                .map(customer -> ResponseEntity.ok(customerDtoMapper.toResponse(customer)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Busca um cliente por DOCUMENT
     * @param document DOCUMENT do cliente
     * @return Cliente encontrado ou 404
     */
    @GetMapping("/document/{document}")
    @Operation(summary = "Buscar cliente por DOCUMENT", description = "Retorna um cliente específico pelo seu DOCUMENT")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content)
    })
    public ResponseEntity<CustomerResponse> getCustomerByDocument(
            @Parameter(description = "DOCUMENT do cliente", example = "123.456.789-00", required = true)
            @PathVariable String document) {
        return customerUseCase.getCustomerByDocument(document)
                .map(customer -> ResponseEntity.ok(customerDtoMapper.toResponse(customer)))
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Cria um novo cliente
     * @param request Dados do cliente
     * @return Cliente criado
     */
    @PostMapping
    @Operation(summary = "Criar novo cliente", description = "Cria um novo cliente com os dados fornecidos")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos",
                    content = @Content)
    })
    public ResponseEntity<CustomerResponse> createCustomer(
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados do cliente a ser criado", required = true,
                    content = @Content(schema = @Schema(implementation = CustomerRequest.class)))
            @Valid @RequestBody CustomerRequest request) {
        try {
            Customer customer = customerDtoMapper.toDomain(request);
            Customer createdCustomer = customerUseCase.createCustomer(customer);
            return new ResponseEntity<>(customerDtoMapper.toResponse(createdCustomer), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    /**
     * Atualiza um cliente existente
     * @param id ID do cliente
     * @param request Dados atualizados
     * @return Cliente atualizado ou 404
     */
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CustomerResponse.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content)
    })
    public ResponseEntity<CustomerResponse> updateCustomer(
            @Parameter(description = "ID do cliente", example = "1", required = true)
            @PathVariable Long id,
            @io.swagger.v3.oas.annotations.parameters.RequestBody(description = "Dados atualizados do cliente", required = true,
                    content = @Content(schema = @Schema(implementation = CustomerRequest.class)))
            @Valid @RequestBody CustomerRequest request) {
        
        return customerUseCase.getCustomerById(id)
                .map(existingCustomer -> {
                    customerDtoMapper.updateDomainFromRequest(request, existingCustomer);
                    Customer updatedCustomer = customerUseCase.updateCustomer(id, existingCustomer);
                    return ResponseEntity.ok(customerDtoMapper.toResponse(updatedCustomer));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * Remove um cliente
     * @param id ID do cliente
     * @return 204 sem conteúdo ou 404
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Remover cliente", description = "Remove um cliente pelo seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado",
                    content = @Content)
    })
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "ID do cliente", example = "1", required = true)
            @PathVariable Long id) {
        return customerUseCase.getCustomerById(id)
                .map(customer -> {
                    customerUseCase.deleteCustomer(id);
                    return new ResponseEntity<Void>(HttpStatus.NO_CONTENT);
                })
                .orElse(ResponseEntity.notFound().build());
    }
} 