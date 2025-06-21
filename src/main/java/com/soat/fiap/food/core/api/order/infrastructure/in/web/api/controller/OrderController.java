package com.soat.fiap.food.core.api.order.infrastructure.in.web.api.controller;

import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.controller.web.api.GetActiveOrdersSortedController;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.controller.web.api.SaveOrderController;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.controller.web.api.UpdateOrderStatusController;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.request.CreateOrderRequest;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.request.OrderStatusRequest;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.response.OrderResponse;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.response.OrderStatusResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para gerenciamento de pedidos
 */
@RestController
@RequestMapping("/orders")
@Tag(name = "Pedidos", description = "API para gerenciamento de pedidos")
@Slf4j
public class OrderController {

    private final OrderDataSource orderDataSource;
    private final UserDataSource userDataSource;
    private final CatalogDataSource catalogDataSource;
    private final PaymentDataSource paymentDataSource;
    private final EventPublisherSource eventPublisherSource;

    public OrderController(OrderDataSource orderDataSource,
                           UserDataSource userDataSource,
                           CatalogDataSource catalogDataSource,
                           PaymentDataSource paymentDataSource,
                           EventPublisherSource eventPublisherSource) {
        this.orderDataSource = orderDataSource;
        this.userDataSource = userDataSource;
        this.catalogDataSource = catalogDataSource;
        this.eventPublisherSource = eventPublisherSource;
        this.paymentDataSource = paymentDataSource;
    }

    @PostMapping
    @Operation(
            summary = "Criar novo pedido",
            description = "Cria um novo pedido com os itens, produtos e descontos especificados",
            security = @SecurityRequirement(name = "bearer-key")
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
    @Transactional
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody CreateOrderRequest createOrderRequest) {
        log.debug("Requisição para criar novo pedido recebida");
        OrderResponse orderResponse = SaveOrderController.saveOrder(
                createOrderRequest,
                orderDataSource,
                userDataSource,
                catalogDataSource,
                eventPublisherSource);
        return ResponseEntity.status(201).body(orderResponse);
    }

    @GetMapping("/active")
    @Operation(
            summary = "Listar pedidos ativos ordenados",
            description = "Retorna todos os pedidos com status RECEBIDO, EM_PREPARACAO ou PRONTO, ordenados por prioridade e data de criação",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Lista de pedidos ativos retornada com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderResponse.class))
            )
    })
    @Transactional(readOnly = true)
    public ResponseEntity<List<OrderResponse>> getActiveOrders() {
        log.debug("Requisição para listar pedidos ativos recebida");

        List<OrderResponse> activeOrders = GetActiveOrdersSortedController.getActiveOrdersSorted(orderDataSource);

        return ResponseEntity.ok(activeOrders);
    }

    @PatchMapping("/{orderId}/status")
    @Operation(
            summary = "Atualizar status do pedido",
            description = "Atualiza o status de um pedido existente para um dos valores válidos: RECEIVED, PREPARING, READY, COMPLETED",
            security = @SecurityRequirement(name = "bearer-key")
    )
    @ApiResponses(value = {
            @ApiResponse(
                    responseCode = "200",
                    description = "Status do pedido atualizado com sucesso",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OrderStatusResponse.class))
            ),
            @ApiResponse(responseCode = "400", description = "Status inválido ou dados malformados", content = @Content),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado", content = @Content)
    })
    @Transactional
    public ResponseEntity<OrderStatusResponse> updateOrderStatus(
            @PathVariable Long orderId,
            @Valid @RequestBody OrderStatusRequest orderStatusRequest) {

        log.debug("Requisição para atualizar status do pedido {} recebida", orderId);

        OrderStatusResponse response = UpdateOrderStatusController.updateOrderStatus(
                orderId,
                orderStatusRequest,
                orderDataSource,
                paymentDataSource,
                eventPublisherSource);

        return ResponseEntity.ok(response);
    }
} 