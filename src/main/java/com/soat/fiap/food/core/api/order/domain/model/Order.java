package com.soat.fiap.food.core.api.order.domain.model;

import com.soat.fiap.food.core.api.order.domain.exceptions.OrderException;
import com.soat.fiap.food.core.api.order.domain.vo.OrderNumber;
import com.soat.fiap.food.core.api.order.domain.vo.OrderPaymentStatus;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import com.soat.fiap.food.core.api.shared.vo.AuditInfo;
import lombok.Data;
import org.apache.commons.lang3.Validate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Entidade de domínio que representa um pedido
 * AGGREGATE ROOT:
 *  - Toda modificação de entidades internas do agregado devem passar pela entidade raíz;
 *  - Único ponto de entrada para qualquer entidade interna do agregado (Lei de Demeter);
 *  - Entidades dentro deste agregado podem se referenciar via id ou objeto;
 *  - Entidades de outros agregados só podem referenciar esta entidade raiz, e isto deve ser via Id;
 */
@Data
public class Order {

    private Long id;
    private Long customerId;
    private OrderNumber orderNumber;
    private OrderStatus orderStatus = OrderStatus.RECEIVED;
    private BigDecimal amount;
    private final AuditInfo auditInfo = new AuditInfo();

    private List<OrderItem> orderItems;
    private List<OrderPayment> orderPayments;

    /**
     * Construtor que cria uma nova instância de pedido com os dados fornecidos.
     *
     * @param customerId   ID do cliente que realizou o pedido
     * @param orderNumber  Número identificador do pedido
     * @param orderStatus  Status atual do pedido
     * @param orderItems   Lista de itens do pedido
     * @throws NullPointerException     se customerId, orderNumber, orderStatus ou amount forem nulos
     * @throws IllegalArgumentException se orderItems for vazio ou se o valor calculado do pedido for menor ou igual a zero
     */
    public Order(
            Long customerId,
            OrderNumber orderNumber,
            OrderStatus orderStatus,
            List<OrderItem> orderItems
    ) {
        validate(customerId, orderNumber, orderStatus, orderItems);
        this.customerId = customerId;
        this.orderNumber = orderNumber;
        this.orderStatus = orderStatus;

        for (OrderItem orderItem : orderItems) {
            addItem(orderItem);
        }
    }

    /**
     * Validação centralizada.
     *
     * @param customerId   ID do cliente
     * @param orderNumber  Número do pedido
     * @param orderStatus  Status do pedido
     * @param orderItems   Lista de itens do pedido
     * @throws NullPointerException     se qualquer parâmetro obrigatório for nulo
     * @throws IllegalArgumentException se a lista de itens estiver vazia ou se o valor for menor ou igual a zero
     */
    private void validate(
            Long customerId,
            OrderNumber orderNumber,
            OrderStatus orderStatus,
            List<OrderItem> orderItems
    ) {
        Objects.requireNonNull(customerId, "O ID do cliente não pode ser nulo");
        Objects.requireNonNull(orderNumber, "O número do pedido não pode ser nulo");
        Objects.requireNonNull(orderStatus, "O status da ordem não pode ser nulo");
        Objects.requireNonNull(orderItems, "A lista de itens da ordem não pode ser nula");

        Validate.notEmpty(orderItems, "A ordem deve conter itens");
    }

    /**
     * Obtém o ID do cliente (se disponível)
     * @return ID do cliente ou null se não houver cliente associado
     */
    public Long getCustomerId() {
        return customerId != null ? customerId : null;
    }

    /**
     * Obtém o número da ordem
     * @return o número da ordem
     */
    public String getOrderNumber() {
        return this.orderNumber.getFormatted();
    }

    /**
     * Fornece uma lista imutável de itens da ordem
     * @return lista imutável de itens
     */
    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(this.orderItems);
    }

    /**
     * Fornece uma lista imutável de pagamentos da ordem
     * @return lista imutável de pagamentos
     */
    public List<OrderPayment> getOrderPayments() {
        return Collections.unmodifiableList(this.orderPayments);
    }

    /**
     * Adiciona um item ao pedido
     * @param item Item a ser adicionado
     * @throws NullPointerException se o item da ordem for nulo
     */
    public void addItem(OrderItem item) {
        Objects.requireNonNull(item, "O item da ordem não pode ser nulo");
        orderItems = (orderItems == null) ? new ArrayList<>() : orderItems;
        orderItems.add(item);
        calculateTotalAmount();
    }

    /**
     * Remove um item do pedido
     * @param item Item a ser removido
     * @throws NullPointerException se o item da ordem for nulo
     */
    public void removeItem(OrderItem item) {
        Objects.requireNonNull(item, "O item da ordem não pode ser nulo");

        if (orderItems != null) {
            orderItems.remove(item);
            calculateTotalAmount();
        }
    }

    /**
     * Calcula o valor total do pedido
     */
    private void calculateTotalAmount() {
        amount = orderItems.stream()
                .map(OrderItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        Validate.isTrue(amount.compareTo(BigDecimal.ZERO) > 0, "O valor do pedido deve ser maior que 0");
    }

    /**
     * Atualiza o status do pedido
     * @param newStatus Novo status
     * @throws NullPointerException se o status da ordem for nulo
     * @throws OrderException se a transição de status não for permitida
     */
    public void updateStatus(OrderStatus newStatus) {
        Objects.requireNonNull(newStatus, "O status da ordem não pode ser nulo");

        if (this.orderStatus == newStatus) {
            return;
        }

        validateStatusTransition(newStatus);

        this.orderStatus = newStatus;
        this.auditInfo.setUpdatedAt(LocalDateTime.now());
    }

    /**
     * Valida se a transição de status é permitida
     * @param newStatus Novo status a ser validado
     * @throws NullPointerException se o status da ordem for nulo
     * @throws OrderException se a transição não for permitida
     */
    private void validateStatusTransition(OrderStatus newStatus) {
        Objects.requireNonNull(newStatus, "O status da ordem não pode ser nulo");

        if (this.orderStatus == OrderStatus.CANCELLED) {
            throw new OrderException(
                    "Não é possível alterar o status de um pedido cancelado"
            );
        }
    }

    /**
     * Atualiza o status do pagamento
     *
     * @param newStatus Novo status
     * @param orderPaymentId Id do pagamento da ordem
     * @throws NullPointerException se o status de pagamento ou id forem nulos
     * @throws OrderException se o pagamento não for encontrado, não estiver pendente ou a ordem não estiver aguardando pagamento
     */
    public void updateOrderPaymentStatus(Long orderPaymentId, OrderPaymentStatus newStatus) {
        Objects.requireNonNull(newStatus, "O status de pagamento não pode ser nulo");
        Objects.requireNonNull(orderPaymentId, "O id do pagamento da ordem não pode ser nulo");

        var orderPayment = orderPayments.stream()
                .filter(o -> o.getId().equals(orderPaymentId))
                .findFirst()
                .orElseThrow(() -> new OrderException("Pagamento da ordem não encontrado"));

        if (orderPayment.getStatus() == newStatus) {
            return;
        }
        if (!(orderPayment.getStatus() == OrderPaymentStatus.PENDING)) {
            throw new OrderException(String.format("Ordem deve estar pendente de pagamento: %s", orderPayment.getStatus()));
        }
        if (!(this.orderStatus == OrderStatus.RECEIVED)) {
            throw new OrderException(String.format("Ordem deve estar aguardando pagamento: %s", this.getOrderStatus()));
        }

        orderPayment.setStatus(newStatus);
        orderPayment.setPaidAt((newStatus == OrderPaymentStatus.APPROVED) ? LocalDateTime.now() : orderPayment.getPaidAt());
        orderPayment.markUpdatedNow();
    }

    /**
     * Retorna se o pagamento da ordem foi aprovado
     * @return true se tiver pagamento aprovado, false caso contrário
     */
    public boolean hasApprovedPayment() {
        if (orderPayments == null) {
            return false;
        }

        return orderPayments
                .stream()
                .anyMatch(OrderPayment::isApproved);
    }
}
