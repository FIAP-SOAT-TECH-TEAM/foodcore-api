package com.soat.fiap.food.core.api.payment.domain.model;

import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

/**
 * Entidade de domínio que representa um pagamento
 * AGGREGATE ROOT:
 * - Toda modificação de entidades internas do agregado devem passar pela
 * entidade raíz;
 * - Único ponto de entrada para qualquer entidade interna do agregado (Lei de
 * Demeter);
 * - Entidades dentro deste agregado podem se referenciar via id ou objeto;
 * - Entidades de outros agregados só podem referenciar esta entidade raiz, e
 * isto deve ser via Id;
 */
@Data
public class Payment {
    private Long id;
    private Long customerId;
    private PaymentMethod type;
    private LocalDateTime expiresIn;
    private String tid;
    private BigDecimal amount;
    private String qrCodeUrl;
    private String observations;
    private final AuditInfo auditInfo = new AuditInfo();

    /**
     * Construtor que cria uma nova instância de pedido com os dados fornecidos.
     *
     * @param customerId  ID do cliente que realizou o pedido
     * @param orderNumber Número identificador do pedido
     * @param orderStatus Status atual do pedido
     * @param orderItems  Lista de itens do pedido
     * @throws NullPointerException     se customerId, orderNumber, orderStatus ou
     *                                  amount forem nulos
     * @throws IllegalArgumentException se orderItems for vazio ou se o valor
     *                                  calculado do pedido for menor ou igual a
     *                                  zero
     */
    public Payment(
            Long customerId,
            PaymentMethod type,
            LocalDateTime expiresIn,
            String tid,
            BigDecimal amount,
            String qrCodeUrl,
            String observations) {

        validate(type, expiresIn, tid, amount);

        this.customerId = customerId;
        this.type = type;
        this.expiresIn = expiresIn;
        this.tid = tid;
        this.amount = amount;
        this.qrCodeUrl = qrCodeUrl;
        this.observations = observations;
    }

    /**
     * Valida os campos obrigatórios para um pagamento.
     *
     * @param type      o tipo do método de pagamento; não pode ser {@code null}
     * @param expiresIn a data e hora de expiração do pagamento; não pode ser
     *                  {@code null}
     * @param tid       o identificador da transação (TID); não pode ser
     *                  {@code null}
     * @param amount    o valor total do pagamento; não pode ser {@code null}
     * @throws NullPointerException se algum dos parâmetros for {@code null}
     */
    private void validate(
            PaymentMethod type,
            LocalDateTime expiresIn,
            String tid,
            BigDecimal amount) {
        Objects.requireNonNull(type, "O tipo do pagamento não pode ser nulo");
        Objects.requireNonNull(expiresIn, "A data de expiração não pode ser nulo");
        Objects.requireNonNull(tid, "O TID não pode ser nulo");
        Objects.requireNonNull(amount, "O valor total não pode ser nulo");
    }

}