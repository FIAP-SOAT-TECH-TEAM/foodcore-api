package com.soat.fiap.food.core.api.payment.infrastructure.adapters.out.persistence.mapper.shared;

import com.soat.fiap.food.core.api.payment.domain.vo.QrCode;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

/**
 * Mapper que converte value object QrCode para string e vice-versa
 */
@Mapper(componentModel = "spring")
public interface QrCodeMapper {

    default String toString(QrCode qrCode) {
        return qrCode != null ? qrCode.value() : null;
    }

    default QrCode toQrCode(String code) {
        return code != null ? new QrCode(code) : null;
    }
}
