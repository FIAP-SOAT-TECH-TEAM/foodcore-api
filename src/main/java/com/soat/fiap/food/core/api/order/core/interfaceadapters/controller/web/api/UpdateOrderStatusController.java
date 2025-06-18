package com.soat.fiap.food.core.api.order.core.interfaceadapters.controller.web.api;

import com.soat.fiap.food.core.api.order.core.application.usecases.EnsureOrderPaymentIsValidUseCase;
import com.soat.fiap.food.core.api.order.core.application.usecases.PublishOrderCanceledEventUseCase;
import com.soat.fiap.food.core.api.order.core.application.usecases.UpdateOrderStatusUseCase;
import com.soat.fiap.food.core.api.order.core.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.gateways.OrderGateway;
import com.soat.fiap.food.core.api.order.core.interfaceadapters.presenter.web.api.OrderPresenter;
import com.soat.fiap.food.core.api.order.infrastructure.common.source.OrderDataSource;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.request.UpdateOrderStatusRequest;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.response.OrderStatusResponse;
import com.soat.fiap.food.core.api.payment.infrastructure.common.source.PaymentDataSource;
import com.soat.fiap.food.core.api.payment.interfaceadapters.gateways.PaymentGateway;
import com.soat.fiap.food.core.api.shared.core.interfaceadapters.gateways.EventPublisherGateway;
import com.soat.fiap.food.core.api.shared.infrastructure.common.source.EventPublisherSource;
import lombok.extern.slf4j.Slf4j;

/**
 * Controller: Atualizar status do pedido.
 *
 */
@Slf4j
public class UpdateOrderStatusController {

    /**
     * Atualizaa status do pedido.


     * @param id ID do pedido
     * @param updateOrderStatusRequest Status do pedido a ser atualizado
     * @param orderDataSource Origem de dados para o gateway de pedido
     * @param paymentDataSource Origem de dados para o gateway de pagamento
     * @param eventPublisherSource  Origem de publicação de eventos
     * @return Pedido atualizado
     */
    public static OrderStatusResponse updateOrderStatus(
            Long id,
            UpdateOrderStatusRequest updateOrderStatusRequest,
            OrderDataSource orderDataSource,
            PaymentDataSource paymentDataSource,
            EventPublisherSource eventPublisherSource) {

        var orderGateway = new OrderGateway(orderDataSource);
        var paymentGateway = new PaymentGateway(paymentDataSource);
        var eventPublisherGateway = new EventPublisherGateway(eventPublisherSource);

        EnsureOrderPaymentIsValidUseCase.ensureOrderPaymentIsValid(id, paymentGateway, orderGateway);

        log.info("Atualizando status do pedido {} para {}", id, updateOrderStatusRequest.getStatus());

        var order = UpdateOrderStatusUseCase.updateOrderStatus(id, updateOrderStatusRequest.getStatus(), orderGateway);
        var updatedOrder = orderGateway.save(order);

        log.info("Status do pedido {} atualizado para {}", id, updatedOrder);

        if (updatedOrder.getOrderStatus() == OrderStatus.CANCELLED) {
            PublishOrderCanceledEventUseCase.publishOrderCanceledEvent(order, eventPublisherGateway);

        }

        return OrderPresenter.toOrderStatusResponse(updatedOrder);
    }
}
