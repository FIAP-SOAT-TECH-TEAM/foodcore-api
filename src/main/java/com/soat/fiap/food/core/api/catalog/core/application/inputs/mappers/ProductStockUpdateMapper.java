package com.soat.fiap.food.core.api.catalog.core.application.inputs.mappers;

import com.soat.fiap.food.core.api.catalog.core.application.inputs.ProductStockUpdateInput;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderItemCanceledEvent;
import com.soat.fiap.food.core.api.order.core.domain.events.OrderItemCreatedEvent;

import java.util.List;

/**
 * Classe utilitária responsável por mapear objetos entre diferentes camadas
 * da aplicação, especialmente entre eventos de domínio (pedidos)
 * e DTOs de entrada da aplicação para atualização de estoque de produtos.
 *
 * Atua como um tradutor entre {@link OrderItemCreatedEvent} ou {@link OrderItemCanceledEvent}
 * e {@link ProductStockUpdateInput}, utilizado para descontar ou repor quantidades em estoque.
 */
public class ProductStockUpdateMapper {

    /**
     * Converte uma lista de {@link OrderItemCreatedEvent} (evento de criação de item de pedido)
     * em um {@link ProductStockUpdateInput} (input da aplicação).
     *
     * Usado para reduzir a quantidade de produtos em estoque.
     *
     * @param events Lista de eventos de criação de itens de pedido.
     * @return Um objeto {@link ProductStockUpdateInput} representando os dados de desconto de estoque.
     */
    public static ProductStockUpdateInput toInput(List<OrderItemCreatedEvent> events) {
        List<ProductStockUpdateInput.ProductStockItemInput> items = events.stream()
                .map(event -> new ProductStockUpdateInput.ProductStockItemInput(
                        event.getProductId(),
                        event.getQuantity()
                ))
                .toList();

        return new ProductStockUpdateInput(items);
    }

    /**
     * Converte uma lista de {@link OrderItemCanceledEvent} (evento de cancelamento de item de pedido)
     * em um {@link ProductStockUpdateInput} (input da aplicação).
     *
     * Usado para repor a quantidade de produtos em estoque.
     *
     * @param events Lista de eventos de cancelamento de itens de pedido.
     * @return Um objeto {@link ProductStockUpdateInput} representando os dados de reposição de estoque.
     */
    public static ProductStockUpdateInput toInputFromCanceled(List<OrderItemCanceledEvent> events) {
        List<ProductStockUpdateInput.ProductStockItemInput> items = events.stream()
                .map(event -> new ProductStockUpdateInput.ProductStockItemInput(
                        event.getProductId(),
                        event.getQuantity()
                ))
                .toList();

        return new ProductStockUpdateInput(items);
    }
}