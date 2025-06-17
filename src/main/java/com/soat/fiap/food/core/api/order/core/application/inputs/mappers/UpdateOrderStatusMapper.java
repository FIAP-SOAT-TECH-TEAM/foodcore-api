package com.soat.fiap.food.core.api.order.core.application.inputs.mappers;

import com.soat.fiap.food.core.api.order.core.application.inputs.UpdateOrderStatusInput;
import com.soat.fiap.food.core.api.order.infrastructure.in.web.api.dto.request.UpdateOrderStatusRequest;

/**
 * Classe utilitária responsável por mapear objetos entre {@link UpdateOrderStatusRequest} e {@link UpdateOrderStatusInput}.
 */
public class UpdateOrderStatusMapper {

    /**
     * Converte um {@link UpdateOrderStatusRequest} (camada web.api) em um {@link UpdateOrderStatusInput} (camada de aplicação).
     *
     * @param request O DTO de atualização de status recebido via API.
     * @return Um objeto {@link UpdateOrderStatusInput} com o novo status.
     */
    public static UpdateOrderStatusInput toInput(UpdateOrderStatusRequest request) {
        return new UpdateOrderStatusInput(request.getStatus());
    }
}