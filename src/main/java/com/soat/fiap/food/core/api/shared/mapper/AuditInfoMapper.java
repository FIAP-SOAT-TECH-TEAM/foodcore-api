package com.soat.fiap.food.core.api.shared.mapper;

import com.soat.fiap.food.core.api.shared.domain.vo.AuditInfo;
import org.mapstruct.Named;

import java.time.LocalDateTime;

/**
 * Mapper que converte um objeto AuditInfo em campos simples
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
