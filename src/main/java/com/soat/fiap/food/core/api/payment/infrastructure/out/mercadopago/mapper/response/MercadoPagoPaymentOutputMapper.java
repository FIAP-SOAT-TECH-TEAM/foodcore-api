package com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.mapper.response;

import com.soat.fiap.food.core.api.payment.core.application.outputs.AcquirerPaymentOutput;
import com.soat.fiap.food.core.api.payment.infrastructure.out.mercadopago.entity.MercadoPagoPaymentEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

/**
 * Mapper responsável por converter {@link MercadoPagoPaymentEntity} em {@link AcquirerPaymentOutput}.
 *
 * Esse mapper é utilizado para extrair apenas os dados relevantes da resposta da adquirente (Mercado Pago)
 * que serão utilizados na lógica de aplicação (caso de uso).
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MercadoPagoPaymentOutputMapper {

    /**
     * Converte uma entidade de resposta do Mercado Pago em um output simplificado
     * contendo apenas os dados necessários para processar a notificação de pagamento.
     *
     * @param entity entidade de pagamento retornada pela API do Mercado Pago
     * @return objeto de saída da camada de aplicação com os dados mapeados
     */
    @Mapping(target = "tid", expression = "java(entity.getId().toString())")
    @Mapping(target = "externalReference", expression = "java(Long.parseLong(entity.getExternal_reference()))")
    @Mapping(target = "type", expression = "java(entity.getPaymentType())")
    @Mapping(target = "status", source = "status")
    AcquirerPaymentOutput toOutput(MercadoPagoPaymentEntity entity);
}

