/**
 * Módulo de Produtos - Gerencia o catálogo de produtos disponíveis para venda,
 * organizados por categorias.
 * <p>
 * Responsabilidades: - CRUD de produtos e categorias - Gestão de imagens de
 * produtos com integração com blob storage - Emissão de eventos para outros
 * módulos
 */
@ApplicationModule(displayName = "Product Module", allowedDependencies = {"com.soat.fiap.food.core.api.shared"})
package com.soat.fiap.food.core.api.catalog;

import org.springframework.modulith.ApplicationModule;
