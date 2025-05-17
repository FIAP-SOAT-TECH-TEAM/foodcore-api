package com.soat.fiap.food.core.api.order.domain.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.aspectj.apache.bcel.classfile.Module;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

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
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Order {
    private Long id;
    private Long customerId;
    private String orderNumber;
    private OrderStatus status = OrderStatus.RECEIVED;
    private BigDecimal amount;
    private AuditInfo auditInfo;

    private List<OrderItem> orderItems;
    private List<OrderPayment> orderPayments;
    
    /**
     * Obtém o ID do cliente (se disponível)
     * @return ID do cliente ou null se não houver cliente associado
     */
    public Long getCustomerId() {
        return customerId != null ? customerId : null;
    }

    /**
     * Fornece uma lista imutável de itens da ordem
     */
    public List<OrderItem> getOrderItems() {
        return Collections.unmodifiableList(this.orderItems);
    }

    /**
     * Fornece uma lista imutável de pagamentos da ordem
     */
    public List<OrderPayment> getOrderPayments() {
        return Collections.unmodifiableList(this.orderPayments);
    }
    
    /**
     * Adiciona um item ao pedido
     * @param item Item a ser adicionado
     */
    public void addItem(OrderItem item) {
        if (orderItems == null) {
            orderItems = new ArrayList<>();
        }
        orderItems.add(item);
        calculateTotalAmount();
    }
    
    /**
     * Remove um item do pedido
     * @param item Item a ser removido
     */
    public void removeItem(OrderItem item) {
        if (orderItems != null) {
            orderItems.remove(item);
            calculateTotalAmount();
        }
    }
    
    /**
     * Calcula o valor total do pedido
     */
    public void calculateTotalAmount() {
        if (orderItems == null || orderItems.isEmpty()) {
            amount = BigDecimal.ZERO;
            return;
        }
        
        amount = orderItems.stream()
                .map(OrderItem::getSubTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }
    
    /**
     * Atualiza o status do pedido
     * @param newStatus Novo status
     * @throws NullPointerException se o status da ordem for nulo
     * @throws IllegalStateException se a transição de status não for permitida
     */
    public void updateStatus(OrderStatus newStatus) {

        Objects.requireNonNull(newStatus, "O status da ordem não pode ser nulo");
        
        if (this.status == newStatus) {
            return;
        }
        
        validateStatusTransition(newStatus);
        
        this.status = newStatus;
        this.auditInfo.setUpdatedAt(LocalDateTime.now());
    }
    
    /**
     * Valida se a transição de status é permitida
     * @param newStatus Novo status a ser validado
     * @throws IllegalStateException se a transição não for permitida
     */
    private void validateStatusTransition(OrderStatus newStatus) {
        if (this.status == OrderStatus.CANCELLED) {
            throw new IllegalStateException(
                "Não é possível alterar o status de um pedido cancelado"
            );
        }
    }

    /**
     * Atualiza o status do pagamento
     *
     * @param newStatus Novo status
     * @param orderPaymentId Id do pagamento da ordem
     * @throws NullPointerException se o status de pagamento for nulo
     * @throws NullPointerException se o id de pagamento da ordem for nulo
     * @throws IllegalArgumentException se o pagamento da ordem não for encontrado
     * @throws IllegalArgumentException se o pagamento da ordem não estiver pendente
     * @throws IllegalArgumentException se a ordem não estiver aguardando pagamento
     */
    public void updateOrderPaymentStatus(Long orderPaymentId, OrderPaymentStatus newStatus) {

        Objects.requireNonNull(newStatus, "O status de pagamento não pode ser nulo");
        Objects.requireNonNull(orderPaymentId, "O id do pagamento da ordem não pode ser nulo");

        var orderPayment = orderPayments.stream()
                .filter(o -> o.getId().equals(orderPaymentId))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("Pagamento da ordem não encontrado"));


        if (orderPayment.getStatus() == newStatus) {
            return;
        }
        if (!(orderPayment.getStatus() == OrderPaymentStatus.PENDING)) {
            throw new IllegalArgumentException(String.format("Ordem deve estar pendente de pagamento: %s", orderPayment.getStatus()));
        }
        if (!(this.status == OrderStatus.RECEIVED)) {
            throw new IllegalArgumentException(String.format("Ordem deve estar aguardando pagamento: %s", this.getStatus()));
        }

        orderPayment.setStatus(newStatus);
        orderPayment.setPaidAt((newStatus == OrderPaymentStatus.APPROVED) ? LocalDateTime.now() : orderPayment.getPaidAt());
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