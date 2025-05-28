package com.soat.fiap.food.core.api.payment.domain.model;

import com.soat.fiap.food.core.api.payment.domain.exceptions.PaymentException;
import com.soat.fiap.food.core.api.payment.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.api.payment.domain.vo.QrCode;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import com.soat.fiap.food.core.api.shared.vo.AuditInfo;

/**
 * Entidade de domínio que representa um pagamento
 */
@Data
public class Payment {
    private Long id;
    private Long userId;
    private Long orderId;
    private PaymentMethod type = PaymentMethod.PIX;
    private LocalDateTime expiresIn;
    private String tid;
    private BigDecimal amount;
    private QrCode qrCode;
    private String observations = "Pagamento via Mercado Pago";
    private AuditInfo auditInfo = new AuditInfo();

    /**
     * Construtor que cria uma nova instância de pagamento com os dados fornecidos.
     *
     * @param userId ID do usuário que realizou o pedido
     * @param expiresIn Data e hora de expiração do pagamento
     * @param tid Identificador da transação (TID)
     * @param amount Valor total do pagamento
     * @param qrCode URL do QR Code do pagamento
     * 
     * @throws NullPointerException se type, expiresIn, tid ou amount forem nulos
     * 
     */
    public Payment(
            Long userId,
            Long orderId,
            LocalDateTime expiresIn,
            String tid,
            BigDecimal amount,
            String qrCode) {

        validate(orderId, type, expiresIn, tid, amount);

        this.userId = userId;
        this.orderId = orderId;
        this.expiresIn = expiresIn;
        this.tid = tid;
        this.amount = amount;
        this.qrCode = new QrCode(qrCode);
    }

    /**
     * Valida os campos obrigatórios para um pagamento.
     *
     * @param type      o tipo do método de pagamento; não pode ser {@code null}
     * @param expiresIn a data e hora de expiração do pagamento; não pode ser {@code null}
     * @param tid       o identificador da transação (TID); não pode ser {@code null}
     * @param amount    o valor total do pagamento; não pode ser {@code null}
     * 
     * @throws NullPointerException se algum dos parâmetros for {@code null}
     * @throws IllegalArgumentException se tid for maior que {@code 255}
     */
    private void validate(
            Long orderId,
            PaymentMethod type,
            LocalDateTime expiresIn,
            String tid,
            BigDecimal amount) {
        Objects.requireNonNull(orderId, "O ID do pedido não pode ser nulo");
        Objects.requireNonNull(type, "O tipo do pagamento não pode ser nulo");
        Objects.requireNonNull(expiresIn, "A data de expiração não pode ser nulo");
        Objects.requireNonNull(tid, "O TID não pode ser nulo");
        Objects.requireNonNull(amount, "O valor total não pode ser nulo");
            
        if (tid.length() > 255) {
            throw new PaymentException("O TID não pode ter mais de 255 caracteres");
        }
    }

    /**
     * Verifica se o pagamento está expirado.
     *
     * @return {@code true} se o pagamento estiver expirado, {@code false} caso contrário
     */
    public boolean isExpired() {
        return expiresIn.isBefore(LocalDateTime.now());
    }

}