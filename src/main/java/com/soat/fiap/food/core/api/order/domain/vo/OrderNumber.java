package com.soat.fiap.food.core.api.order.domain.vo;

import org.apache.commons.lang3.Validate;
import java.io.Serializable;

/**
 * Value Object que representa o número de um pedido.
 * Formato: "ORD-{ANO}-{SEQUENCIAL}" onde:
 * - ANO: 4 dígitos
 * - SEQUENCIAL: 5 dígitos
 * <p>
 * Exemplo: "ORD-2023-00042"
 */
public record OrderNumber(int year, int sequential) implements Serializable {

    /**
     * Cria um novo número de pedido com validação de ano e sequência.
     *
     * @param year       ano com 4 dígitos
     * @param sequential número sequencial com até 5 dígitos
     */
    public OrderNumber {
        validate(year, sequential);
    }

    /**
     * Retorna a representação formatada: "ORD-AAAA-NNNNN"
     *
     * @return String formatada do número do pedido
     */
    public String getFormatted() {
        return String.format("ORD-%04d-%05d", year, sequential);
    }

    /**
     * Validação centralizada.
     */
    private void validate(int year, int sequential) {
        Validate.isTrue(year >= 1000 && year <= 9999, "Ano deve ter 4 dígitos");
        Validate.isTrue(sequential >= 0 && sequential <= 99999, "Sequencial deve ter até 5 dígitos");
    }
}