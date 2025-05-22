package com.soat.fiap.food.core.api.payment.application.ports.in;

import com.soat.fiap.food.core.api.payment.domain.vo.PaymentMethod;
import com.soat.fiap.food.core.api.payment.domain.model.Payment;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface PaymentUseCase {

    /**
     * Aplica desconto promocional ao pagamento.
     * 
     * @param paymentId ID do pagamento
     * @param percent   Percentual de desconto
     */
    void applyDiscount(Long paymentId, int percent);

    /**
     * Lista métodos de pagamento disponíveis.
     * 
     * @return Lista de métodos de pagamento
     */
    List<PaymentMethod> listAvailablePaymentMethods();

    /**
     * Inicializa um novo pagamento.
     * 
     * @param customerId   ID do cliente
     * @param type         Tipo do método de pagamento
     * @param expiresIn    Data/hora de expiração
     * @param tid          Identificador da transação
     * @param amount       Valor total
     * @param qrCode       URL/dados do QR Code
     * @param observations Observações
     * @return Instância de Payment criada
     */
    Payment createPayment(
            Long customerId,
            PaymentMethod type,
            LocalDateTime expiresIn,
            String tid,
            BigDecimal amount,
            String qrCode,
            String observations);

    /**
     * Consulta informações de um pagamento.
     * 
     * @param paymentId ID do pagamento
     * @return Instância de Payment
     */
    Payment getPayment(Long paymentId);

    /**
     * Aprova um pagamento.
     * 
     * @param paymentId ID do pagamento
     */
    void approvePayment(Long paymentId);
}