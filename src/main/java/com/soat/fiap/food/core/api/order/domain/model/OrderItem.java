package com.soat.fiap.food.core.api.order.domain.model;

import com.soat.fiap.food.core.api.order.domain.vo.OrderItemPrice;
import com.soat.fiap.food.core.api.shared.vo.AuditInfo;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Entidade de domínio que representa um item de pedido
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class OrderItem {

    private Long id;
    private Long productId;
    private OrderItemPrice orderItemPrice;
    private String observations = "";

    private AuditInfo auditInfo = new AuditInfo();
    private Order order;

    /**
     * Construtor que cria um item de pedido com os dados informados
     *
     * @param productId        ID do produto
     * @param orderItemPrice   Preço do item
     * @param observations     Observações sobre o item
     * @throws NullPointerException se qualquer parâmetro for nulo
     */
    public OrderItem(
            Long productId,
            OrderItemPrice orderItemPrice,
            String observations
    ) {
        validate(productId, orderItemPrice);

        this.productId = productId;
        this.orderItemPrice = orderItemPrice;
        this.observations = observations;
    }

    /**
     * Validação centralizada.
     *
     * @param productId        ID do produto
     * @param orderItemPrice   Preço do item
     * @throws NullPointerException se qualquer parâmetro for nulo
     */
    private void validate(
            Long productId,
            OrderItemPrice orderItemPrice
    ) {
        Objects.requireNonNull(productId, "O ID do produto não pode ser nulo");
        Objects.requireNonNull(orderItemPrice, "O preço do item da ordem não pode ser nulo");

    }

    /**
     * Retorna o subtotal do item de pedido com base no preço e quantidade
     *
     * @return subtotal do item
     */
    BigDecimal getSubTotal () {
        return this.orderItemPrice.getSubTotal();
    }

    /**
     * Atualiza o campo updatedAt com o horário atual.
     *
     * @throws IllegalStateException se o horário atual for menor ou igual ao createdAt
     */
    void markUpdatedNow() {
        this.auditInfo.setUpdatedAt(LocalDateTime.now());
    }
}