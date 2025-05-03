/**
 * Módulo de Pagamento - Gerencia a integração com gateways de pagamento e o controle
 * das transações financeiras.
 * 
 * Responsabilidades:
 * - Processamento de pagamentos
 * - Integração com gateways de pagamento
 * - Emissão de eventos para outros módulos 
 */
@ApplicationModule(
    displayName = "Payment Module",
    allowedDependencies = {
        "com.soat.fiap.food.core.api.order",
        "com.soat.fiap.food.core.api.shared"
    }
)
package com.soat.fiap.food.core.api.payment;

import org.springframework.modulith.ApplicationModule;
