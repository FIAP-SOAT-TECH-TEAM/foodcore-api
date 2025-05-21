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
    private Long customerId;
    private PaymentMethod type;
    private LocalDateTime expiresIn;
    private String tid;
    private BigDecimal amount;
    private QrCode qrCode;
    private String observations;
    private final AuditInfo auditInfo = new AuditInfo();

    /**
     * Construtor que cria uma nova instância de pagamento com os dados fornecidos.
     *
     * @param customerId ID do cliente que realizou o pedido
     * @param type Tipo do método de pagamento
     * @param expiresIn Data e hora de expiração do pagamento
     * @param tid Identificador da transação (TID)
     * @param amount Valor total do pagamento
     * @param qrCodeUrl URL do QR Code do pagamento
     * @param observations Observações adicionais sobre o pagamento
     * 
     * @throws NullPointerException se type, expiresIn, tid ou amount forem nulos
     * 
     */
    public Payment(
            Long customerId,
            PaymentMethod type,
            LocalDateTime expiresIn,
            String tid,
            BigDecimal amount,
            String qrCode,
            String observations) {

        validate(type, expiresIn, tid, amount);

        this.customerId = customerId;
        this.type = type;
        this.expiresIn = expiresIn;
        this.tid = tid;
        this.amount = amount;
        this.qrCode = new QrCode(qrCode);
        this.observations = observations;
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
            PaymentMethod type,
            LocalDateTime expiresIn,
            String tid,
            BigDecimal amount) {
        Objects.requireNonNull(type, "O tipo do pagamento não pode ser nulo");
        Objects.requireNonNull(expiresIn, "A data de expiração não pode ser nulo");
        Objects.requireNonNull(tid, "O TID não pode ser nulo");
        Objects.requireNonNull(amount, "O valor total não pode ser nulo");
            
        if (tid.length() > 255) {
            throw new IllegalArgumentException("O TID não pode ter mais de 255 caracteres");
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

    /**
     * Aplica um desconto percentual ao valor do pagamento.
     *
     * @param percent o percentual de desconto a ser aplicado (1 a 100)
     * @throws PaymentException se percent não estiver entre 1 e 100
     */
    public void applyDiscount(int percent) {
        if (percent < 1 || percent > 100) {
            throw new PaymentException("O percentual de desconto deve estar entre 1 e 100");
        }
        
        BigDecimal percentage = BigDecimal.valueOf(percent).divide(BigDecimal.valueOf(100));
        BigDecimal discount = this.amount.multiply(percentage);
        this.amount = this.amount.subtract(discount);
    }

}