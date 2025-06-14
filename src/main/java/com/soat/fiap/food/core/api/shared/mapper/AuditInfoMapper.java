package com.soat.fiap.food.core.api.shared.mapper;

import com.soat.fiap.food.core.api.shared.core.domain.vo.AuditInfo;
import org.mapstruct.Named;

import java.time.LocalDateTime;

/**
 * Mapper que converte um objeto AuditInfo em campos simples
 * TODO: excluir após terminar a refatoção para Clean Arch
 * MOTIVO: é usado somente para mapear uma entidade em uma resposta esperada pela camada de infraestrutura
 * Com o clean arch o responsável disto será o Presenter, e ele deve ser puro (sem dependência com outras libs)
 */
public class AuditInfoMapper {

    @Named("mapCreatedAt")
    public static LocalDateTime mapCreatedAt(AuditInfo auditInfo) {
        return auditInfo != null ? auditInfo.getCreatedAt() : null;
    }

    @Named("mapUpdatedAt")
    public static LocalDateTime mapUpdatedAt(AuditInfo auditInfo) {
        return auditInfo != null ? auditInfo.getUpdatedAt() : null;
    }
}
