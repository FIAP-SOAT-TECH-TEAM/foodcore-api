package com.soat.fiap.food.core.api.order.core.interfaceadapters.controller.web.api;

import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.catalog.infrastructure.common.source.CatalogDataSource;
import com.soat.fiap.food.core.api.order.core.application.inputs.mappers.CreateOrderMapper;
import com.soat.fiap.food.core.api.order.core.application.usecases.*;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.gateways.OrderGateway;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.presenter.web.api.OrderPresenter;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.request.CreateOrderRequest;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.response.OrderResponse;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.EventPublisherGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;
import com.soat.fiap.food.core.api.user.infrastructure.common.source.UserDataSource;
import com.soat.fiap.food.core.api.user.core.interfaceadapters.gateways.UserGateway;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Salvar pedido.
 *
 */
@Slf4j
public class SaveOrderController {


    /**
     * Salva um pedido.
     *
     * @param createOrderRequest Pedido a ser salvo
     * @param orderDataSource Origem de dados para o gateway de pedido
     * @param userDataSource Origem de dados para o gateway de usuario
     * @param catalogDataSource Origem de dados para o gateway de catalogo
     * @param eventPublisherSource  Origem de publicação de eventos
     * @return Pedido salvo com identificadores atualizados
     */
    public static OrderResponse saveOrder (
            CreateOrderRequest createOrderRequest,
            OrderDataSource orderDataSource,
            UserDataSource userDataSource,
            CatalogDataSource catalogDataSource,
            EventPublisherSource eventPublisherSource) {

        var orderGateway = new OrderGateway(orderDataSource);
        var catalogGateway = new CatalogGateway(catalogDataSource);
        var userGateway = new UserGateway(userDataSource);
        var eventPublisherGateway = new EventPublisherGateway(eventPublisherSource);

        var orderInput = CreateOrderMapper.toInput(createOrderRequest);
        var order = CreateOrderUseCase.createOrder(orderInput);

        AssignGuestUserToOrderUseCase.assignGuestUserToOrder(order, userGateway);
        EnsureValidOrderItemsUseCase.ensureValidOrderItems(order.getOrderItems(), catalogGateway);
        ApplyDiscountUseCase.applyDiscount(order, userGateway);

        var savedOrder = orderGateway.save(order);

        PublishCreateOrderEventUseCase.publishCreateOrderEvent(order, eventPublisherGateway);

        var saveOrderToResponse = OrderPresenter.toOrderResponse(savedOrder);

        log.info("Pedido {} criado com sucesso. Total: {}", savedOrder.getId(), savedOrder.getAmount());

        return saveOrderToResponse;
    }
}
