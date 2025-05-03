/**
 * Módulo de Produtos - Gerencia o catálogo de produtos disponíveis para venda, organizados por categorias.
 * 
 * Responsabilidades:
 * - CRUD de produtos e categorias
 * - Gestão de imagens de produtos com integração com CDN
 * - Emissão de eventos para outros módulos
 */
@ApplicationModule(
    displayName = "Product Module",
    allowedDependencies = {
        "com.soat.fiap.food.core.api.shared"
    }
)
package com.soat.fiap.food.core.api.product;

import org.springframework.modulith.ApplicationModule;
