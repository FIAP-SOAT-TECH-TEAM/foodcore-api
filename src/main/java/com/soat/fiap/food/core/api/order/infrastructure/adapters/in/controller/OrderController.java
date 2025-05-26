package com.soat.fiap.food.core.api.order.infrastructure.adapters.in.controller;

import com.soat.fiap.food.core.api.customer.domain.ports.out.CustomerRepository;
import com.soat.fiap.food.core.api.customer.domain.model.Customer;
import com.soat.fiap.food.core.api.order.application.ports.in.OrderUseCase;
import com.soat.fiap.food.core.api.order.domain.model.Order;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.in.dto.request.AddOrderItemRequest;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.in.dto.request.CreateOrderRequest;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.in.dto.request.UpdateOrderStatusRequest;
import com.soat.fiap.food.core.api.order.infrastructure.adapters.in.dto.response.OrderResponse;
import com.soat.fiap.food.core.api.order.mapper.OrderDtoMapper;
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

/**
 * Controlador REST para gerenciamento de pedidos
 */
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Pedidos", description = "API para gerenciamento de pedidos")
public class OrderController {

    private final OrderUseCase orderUseCase;
    private final OrderDtoMapper orderDtoMapper;
    private final CustomerRepository customerRepository;
    private final CustomLogger logger;

    public OrderController(
            OrderUseCase orderUseCase,
            OrderDtoMapper orderDtoMapper,
            CustomerRepository customerRepository) {
        this.orderUseCase = orderUseCase;
        this.orderDtoMapper = orderDtoMapper;
        this.customerRepository = customerRepository;
        this.logger = CustomLogger.getLogger(getClass());
    }

    /**
     * Cria um novo pedido
     *
     * @param request Dados do pedido
     * @return Pedido criado
     */
    @PostMapping
    @Operation(
        summary = "Criar novo pedido",
        description = "Cria um novo pedido com os itens fornecidos"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = OrderResponse.class))),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content)
    })
    public ResponseEntity<OrderResponse> createOrder(
            @Valid @RequestBody CreateOrderRequest request) {
        
        logger.info("Requisição para criar pedido recebida. Cliente ID: {}, {} itens", 
                request.getCustomerId(), request.getItems().size());
        
        try {
            List<OrderUseCase.OrderItemRequest> itemRequests = orderDtoMapper.toOrderItemRequests(request);

            Order order = orderUseCase.createOrder(request.getCustomerId(), itemRequests);
            
            if (request.getCustomerId() != null && (order.getCustomerId() == null || order.getCustomerId().getName() == null)) {
                logger.debug("Cliente completo não preenchido, buscando dados do cliente ID: {}", request.getCustomerId());
                
                if (order.getCustomerId() == null) {
                    order.setCustomerId(Customer.builder().id(request.getCustomerId()).build());
                }
                
                customerRepository.findById(request.getCustomerId())
                    .ifPresent(customer -> {
                        logger.debug("Cliente encontrado: {}", customer.getName());
                        order.setCustomerId(customer);
                    });
            }
            
            OrderResponse response = orderDtoMapper.toResponse(order);
            
            logger.info("Pedido criado com sucesso. ID: {}, Total: {}", order.getId(), order.getTotalAmount());
            return new ResponseEntity<>(response, HttpStatus.CREATED);
        } catch (Exception e) {
            logger.error("Erro ao processar a requisição de criação de pedido: {}", e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Lista todos os pedidos por status
     * 
     * @param status Status dos pedidos
     * @return Lista de pedidos
     */
    @GetMapping
    @Operation(
        summary = "Listar pedidos por status", 
        description = "Retorna uma lista com todos os pedidos filtrados pelo status"
    )
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso",
            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                    array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class))))
    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(
            @Parameter(description = "Status do pedido para filtrar", example = "PENDING")
            @RequestParam(required = false) OrderStatus status) {
        
        logger.debug("Requisição para listar pedidos com status: {}", status);
        
        List<Order> orders;
        if (status != null) {
            orders = orderUseCase.findOrdersByStatus(status);
        } else {
            orders = orderUseCase.findOrdersByStatus(OrderStatus.PENDING);
        }
        
        List<OrderResponse> response = orderDtoMapper.toResponseList(orders);
        logger.debug("Retornando {} pedidos", response.size());
        
        return ResponseEntity.ok(response);
    }

    /**
     * Busca um pedido pelo ID
     * 
     * @param id ID do pedido
     * @return Pedido encontrado ou 404
     */
    @GetMapping("/{id}")
    @Operation(
        summary = "Buscar pedido por ID", 
        description = "Retorna os detalhes de um pedido específico"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = OrderResponse.class))),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                content = @Content)
    })
    public ResponseEntity<OrderResponse> getOrderById(
            @Parameter(description = "ID do pedido", example = "1", required = true)
            @PathVariable Long id) {
        
        logger.debug("Requisição para buscar pedido ID: {}", id);
        
        return orderUseCase.findOrderById(id)
                .map(order -> {
                    OrderResponse response = orderDtoMapper.toResponse(order);
                    logger.debug("Pedido {} encontrado", id);
                    return ResponseEntity.ok(response);
                })
                .orElseGet(() -> {
                    logger.warn("Pedido {} não encontrado", id);
                    return ResponseEntity.notFound().build();
                });
    }

    /**
     * Atualiza o status de um pedido
     * 
     * @param id ID do pedido
     * @param request Dados do novo status
     * @return Pedido atualizado
     */
    @PatchMapping("/{id}/status")
    @Operation(
        summary = "Atualizar status do pedido", 
        description = "Atualiza o status de um pedido existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = OrderResponse.class))),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                content = @Content),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content)
    })
    public ResponseEntity<OrderResponse> updateOrderStatus(
            @Parameter(description = "ID do pedido", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderStatusRequest request) {
        
        logger.info("Requisição para atualizar status do pedido {}. Novo status: {}", 
                id, request.getStatus());
        
        try {
            Order order = orderUseCase.updateOrderStatus(id, request.getStatus());
            OrderResponse response = orderDtoMapper.toResponse(order);
            
            logger.info("Status do pedido {} atualizado para {}", id, request.getStatus());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.warn("Pedido {} não encontrado ao tentar atualizar status", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Erro ao atualizar status do pedido {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }

    /**
     * Adiciona um item a um pedido existente
     * 
     * @param id ID do pedido
     * @param request Dados do novo item
     * @return Pedido atualizado
     */
    @PostMapping("/{id}/items")
    @Operation(
        summary = "Adicionar item ao pedido", 
        description = "Adiciona um novo item a um pedido existente"
    )
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Item adicionado com sucesso",
                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                        schema = @Schema(implementation = OrderResponse.class))),
        @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
                content = @Content),
        @ApiResponse(responseCode = "400", description = "Dados inválidos",
                content = @Content)
    })
    public ResponseEntity<OrderResponse> addItemToOrder(
            @Parameter(description = "ID do pedido", example = "1", required = true)
            @PathVariable Long id,
            @Valid @RequestBody AddOrderItemRequest request) {
        
        logger.info("Requisição para adicionar item ao pedido {}. Produto: {}, Quantidade: {}", 
                id, request.getProductId(), request.getQuantity());
        
        try {
            Order order = orderUseCase.addItemToOrder(id, request.getProductId(), request.getQuantity());
            OrderResponse response = orderDtoMapper.toResponse(order);
            
            logger.info("Item adicionado ao pedido {}. Novo total: {}", id, order.getTotalAmount());
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            logger.warn("Pedido {} não encontrado ao tentar adicionar item", id);
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            logger.error("Erro ao adicionar item ao pedido {}: {}", id, e.getMessage(), e);
            throw e;
        }
    }
} 