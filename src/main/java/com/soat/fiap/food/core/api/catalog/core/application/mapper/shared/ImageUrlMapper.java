package com.soat.fiap.food.core.api.catalog.core.application.mapper.shared;

import com.soat.fiap.food.core.api.catalog.core.domain.vo.ImageUrl;
import org.mapstruct.Named;

/**
 * Mapper que converte um record ImageUrl em string
 */
public class ImageUrlMapper {

    @Named("mapImageUrlToString")
    public static String mapImageUrlToString(ImageUrl imageUrl) {
        return imageUrl != null ? imageUrl.imageUrl() : null;
    }
}
