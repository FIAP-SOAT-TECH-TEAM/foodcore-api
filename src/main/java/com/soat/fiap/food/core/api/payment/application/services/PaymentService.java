package com.soat.fiap.food.core.api.payment.application.services;

import com.soat.fiap.food.core.api.payment.application.ports.in.PaymentUseCase;
import com.soat.fiap.food.core.api.payment.application.ports.out.PaymentRepository;
import com.soat.fiap.food.core.api.payment.domain.events.PaymentApprovedEvent;
import com.soat.fiap.food.core.api.payment.domain.model.Payment;
import com.soat.fiap.food.core.api.payment.domain.vo.PaymentMethod;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * Implementação do caso de uso de pagamento
 */
@Service
@Slf4j
public class PaymentService implements PaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final ApplicationEventPublisher eventPublisher;

    public PaymentService(
            PaymentRepository paymentRepository,
            ApplicationEventPublisher eventPublisher) {
        this.paymentRepository = paymentRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void applyDiscount(Long paymentId, int percent) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);

        if (!payment.isPresent()) {
            throw new IllegalArgumentException("Pagamento não encontrado");
        }

        Payment paymentToUpdate = payment.get();

        paymentToUpdate.applyDiscount(percent);

        paymentRepository.save(paymentToUpdate);
    }

    @Override
    public List<PaymentMethod> listAvailablePaymentMethods() {
        return Arrays.asList(PaymentMethod.values());
    }

    @Override
    public Payment createPayment(Long customerId, PaymentMethod type, LocalDateTime expiresIn, String tid,
            BigDecimal amount, String qrCode, String observations) {

        Payment payment = new Payment(
                customerId,
                type,
                expiresIn,
                tid,
                amount,
                qrCode,
                observations);

        return paymentRepository.save(payment);
    }

    @Override
    public Payment getPayment(Long paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);

        if (!payment.isPresent()) {
            throw new IllegalArgumentException("Pagamento não encontrado");
        }

        return payment.get();
    }

    @Override
    public void approvePayment(Long paymentId) {
        this.eventPublisher.publishEvent(
            new PaymentApprovedEvent(paymentId, LocalDateTime.now())
        );
    }
}