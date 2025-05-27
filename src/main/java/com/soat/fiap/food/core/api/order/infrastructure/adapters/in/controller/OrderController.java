package com.soat.fiap.food.core.api.order.infrastructure.adapters.in.controller;

import com.soat.fiap.food.core.api.order.application.dto.request.CreateOrderRequest;
import com.soat.fiap.food.core.api.order.application.dto.response.OrderResponse;
import com.soat.fiap.food.core.api.order.application.ports.in.OrderUseCase;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Controlador REST para gerenciamento de pedidos
 */
@RestController
@RequestMapping("/api/orders")
@Tag(name = "Pedidos", description = "API para gerenciamento de pedidos")
public class OrderController {

    private final OrderUseCase orderUseCase;
    private final CustomLogger logger;

    public OrderController(
            OrderUseCase orderUseCase) {
        this.orderUseCase = orderUseCase;
        this.logger = CustomLogger.getLogger(getClass());
    }

    @PostMapping
    @Operation(
            summary = "Criar novo pedido",
            description = "Cria um novo pedido com os itens, produtos e descontos especificados"
    )

    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "201",
                    description = "Pedido criado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderResponse.class))

            ),
            @ApiResponse(responseCode = "400", description = "Dados inválidos", content = @Content),
            @ApiResponse(responseCode = "404", description = "Cliente ou produto não encontrado", content = @Content)
    })
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        logger.debug("Requisição para criar novo pedido recebida");
        OrderResponse orderResponse = orderUseCase.createOrder(createOrderRequest);
        return ResponseEntity.status(201).body(orderResponse);
    }


//    /**
//     * Lista todos os pedidos por status
//     *
//     * @param status Status dos pedidos
//     * @return Lista de pedidos
//     */
//    @GetMapping
//    @Operation(
//        summary = "Listar pedidos por status",
//        description = "Retorna uma lista com todos os pedidos filtrados pelo status"
//    )
//    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso",
//            content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                    array = @ArraySchema(schema = @Schema(implementation = OrderResponse.class))))
//    public ResponseEntity<List<OrderResponse>> getOrdersByStatus(
//            @Parameter(description = "Status do pedido para filtrar", example = "PENDING")
//            @RequestParam(required = false) OrderStatus status) {
//
//        logger.debug("Requisição para listar pedidos com status: {}", status);
//
//        List<Order> orders;
//        if (status != null) {
//            orders = orderUseCase.findOrdersByStatus(status);
//        } else {
//            orders = orderUseCase.findOrdersByStatus(OrderStatus.PENDING);
//        }
//
//        List<OrderResponse> response = orderDtoMapper.toResponseList(orders);
//        logger.debug("Retornando {} pedidos", response.size());
//
//        return ResponseEntity.ok(response);
//    }
//
//    /**
//     * Busca um pedido pelo ID
//     *
//     * @param id ID do pedido
//     * @return Pedido encontrado ou 404
//     */
//    @GetMapping("/{id}")
//    @Operation(
//        summary = "Buscar pedido por ID",
//        description = "Retorna os detalhes de um pedido específico"
//    )
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Pedido encontrado com sucesso",
//                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                        schema = @Schema(implementation = OrderResponse.class))),
//        @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
//                content = @Content)
//    })
//    public ResponseEntity<OrderResponse> getOrderById(
//            @Parameter(description = "ID do pedido", example = "1", required = true)
//            @PathVariable Long id) {
//
//        logger.debug("Requisição para buscar pedido ID: {}", id);
//
//        return orderUseCase.findOrderById(id)
//                .map(order -> {
//                    OrderResponse response = orderDtoMapper.toResponse(order);
//                    logger.debug("Pedido {} encontrado", id);
//                    return ResponseEntity.ok(response);
//                })
//                .orElseGet(() -> {
//                    logger.warn("Pedido {} não encontrado", id);
//                    return ResponseEntity.notFound().build();
//                });
//    }
//
//    /**
//     * Atualiza o status de um pedido
//     *
//     * @param id ID do pedido
//     * @param request Dados do novo status
//     * @return Pedido atualizado
//     */
//    @PatchMapping("/{id}/status")
//    @Operation(
//        summary = "Atualizar status do pedido",
//        description = "Atualiza o status de um pedido existente"
//    )
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso",
//                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                        schema = @Schema(implementation = OrderResponse.class))),
//        @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
//                content = @Content),
//        @ApiResponse(responseCode = "400", description = "Dados inválidos",
//                content = @Content)
//    })
//    public ResponseEntity<OrderResponse> updateOrderStatus(
//            @Parameter(description = "ID do pedido", example = "1", required = true)
//            @PathVariable Long id,
//            @Valid @RequestBody UpdateOrderStatusRequest request) {
//
//        logger.info("Requisição para atualizar status do pedido {}. Novo status: {}",
//                id, request.getStatus());
//
//        try {
//            Order order = orderUseCase.updateOrderStatus(id, request.getStatus());
//            OrderResponse response = orderDtoMapper.toResponse(order);
//
//            logger.info("Status do pedido {} atualizado para {}", id, request.getStatus());
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            logger.warn("Pedido {} não encontrado ao tentar atualizar status", id);
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            logger.error("Erro ao atualizar status do pedido {}: {}", id, e.getMessage(), e);
//            throw e;
//        }
//    }
//
//    /**
//     * Adiciona um item a um pedido existente
//     *
//     * @param id ID do pedido
//     * @param request Dados do novo item
//     * @return Pedido atualizado
//     */
//    @PostMapping("/{id}/items")
//    @Operation(
//        summary = "Adicionar item ao pedido",
//        description = "Adiciona um novo item a um pedido existente"
//    )
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "200", description = "Item adicionado com sucesso",
//                content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
//                        schema = @Schema(implementation = OrderResponse.class))),
//        @ApiResponse(responseCode = "404", description = "Pedido não encontrado",
//                content = @Content),
//        @ApiResponse(responseCode = "400", description = "Dados inválidos",
//                content = @Content)
//    })
//    public ResponseEntity<OrderResponse> addItemToOrder(
//            @Parameter(description = "ID do pedido", example = "1", required = true)
//            @PathVariable Long id,
//            @Valid @RequestBody AddOrderItemRequest request) {
//
//        logger.info("Requisição para adicionar item ao pedido {}. Produto: {}, Quantidade: {}",
//                id, request.getProductId(), request.getQuantity());
//
//        try {
//            Order order = orderUseCase.addItemToOrder(id, request.getProductId(), request.getQuantity());
//            OrderResponse response = orderDtoMapper.toResponse(order);
//
//            logger.info("Item adicionado ao pedido {}. Novo total: {}", id, order.getTotalAmount());
//            return ResponseEntity.ok(response);
//        } catch (IllegalArgumentException e) {
//            logger.warn("Pedido {} não encontrado ao tentar adicionar item", id);
//            return ResponseEntity.notFound().build();
//        } catch (Exception e) {
//            logger.error("Erro ao adicionar item ao pedido {}: {}", id, e.getMessage(), e);
//            throw e;
//        }
//    }
} 