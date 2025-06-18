package com.soat.fiap.food.core.api.order.core.application.usecases;

import com.soat.fiap.food.core.api.catalog.core.domain.exceptions.ProductNotFoundException;
import com.soat.fiap.food.core.api.catalog.core.interfaceadapters.gateways.CatalogGateway;
import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderException;
import com.soat.fiap.food.core.api.order.core.domain.exceptions.OrderItemException;
import com.soat.fiap.food.core.api.order.core.domain.model.OrderItem;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * Caso de uso: Validar os itens do pedido.
 */
@Slf4j
public class EnsureValidOrderItemsUseCase {

    /**
     * Valida os produtos dos itens do pedido com base no catálogo.
     *
     * @param orderItems Lista de itens do pedido
     * @param gateway Gateway para comunicação com o mundo exterior
     * @throws OrderException se o produto não for encontrado
     * @throws OrderItemException se houver divergência nos dados dos produtos
     */
    public static void ensureValidOrderItems(List<OrderItem> orderItems, CatalogGateway gateway) {

        for (OrderItem orderItem : orderItems) {

            var catalog = gateway.findByProductId(orderItem.getProductId());

            if (catalog.isEmpty()) {
                throw new ProductNotFoundException("O produto do item do pedido não existe");
            }

            var productOrderItem = catalog.get().getProductById(orderItem.getProductId());

            if (!productOrderItem.getName().equals(orderItem.getName())) {
                throw new OrderItemException("O nome do produto do item diverge do nome do produto cadastrado");
            }
            else if (productOrderItem.getPrice().compareTo(orderItem.getUnitPrice()) != 0) {
                throw new OrderItemException("O preço unitário do item do pedido diverge do preço do produto");
            }
            else if (!productOrderItem.isActive()) {
                throw new OrderItemException("O pedido não pode possuir produtos inativos");
            }
            else if (!productOrderItem.categoryisActive()) {
                throw new OrderItemException("A categoria do produto do pedido não pode estar inativa");
            }
            else if (productOrderItem.getStockQuantity() < orderItem.getQuantity()) {
                throw new OrderItemException(String.format("Quantidade insuficiente em estoque para o produto: %s", productOrderItem.getName()));
            }

        }
    }

}
