/**
 * Módulo de Pedidos - Núcleo do sistema, responsável pelo processo de montagem
 * do pedido, registo de saída e acompanhamento dos status.
 * <p>
 * Responsabilidades: - Montagem e gestão de pedidos - Acompanhamento de status
 * do pedido - Emissão de eventos para outros módulos
 */
@ApplicationModule(displayName = "Order Module", allowedDependencies = {"com.soat.fiap.food.core.api.product",
		"com.soat.fiap.food.core.api.userId", "com.soat.fiap.food.core.api.shared"})
package com.soat.fiap.food.core.api.order;

import org.springframework.modulith.ApplicationModule;
