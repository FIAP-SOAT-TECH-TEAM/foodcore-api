/**
 * Módulo de Cliente - Gerencia o cadastro e autenticação de clientes.
 * 
 * Responsabilidades:
 * - Cadastro e gestão de clientes
 * - Autenticação e autorização
 * - Gestão de preferências e histórico
 */
@ApplicationModule(
    displayName = "Customer Module",
    allowedDependencies = {
        "com.soat.fiap.food.core.api.shared"
    }
)
package com.soat.fiap.food.core.api.customer;

import org.springframework.modulith.ApplicationModule;
