package com.soat.fiap.food.core.api.catalog.mapper;

import com.soat.fiap.food.core.api.catalog.domain.model.Category;
import com.soat.fiap.food.core.api.catalog.domain.model.Product;
import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.in.dto.request.ProductRequest;
import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.in.dto.response.ProductResponse;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import com.soat.fiap.food.core.api.shared.infrastructure.storage.ImageStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * Mapper que converte entre DTOs e entidades de domínio para Product
 */
@Component
public class ProductDtoMapper {
    
    private final ImageStorageService imageStorageService;
    private final CategoryDtoMapper categoryDtoMapper;
    private final CustomLogger logger;
    
    @Value("${app.cdn.base-url}")
    private String cdnBaseUrl;
    
    @Autowired
    public ProductDtoMapper(ImageStorageService imageStorageService, CategoryDtoMapper categoryDtoMapper) {
        this.imageStorageService = imageStorageService;
        this.categoryDtoMapper = categoryDtoMapper;
        this.logger = CustomLogger.getLogger(getClass());
    }
    
    /**
     * Converte entidade de domínio para DTO de resposta
     * @param product Entidade de domínio
     * @return DTO de resposta
     */
    public ProductResponse toResponse(Product product) {
        if (product == null) {
            return null;
        }
        
        logger.debug("Convertendo produto para DTO de resposta: {}", product.getName());
        
        ProductResponse response = new ProductResponse();
        response.setId(product.getId());
        response.setName(product.getName());
        response.setDescription(product.getDescription());
        response.setPrice(product.getPrice());
        response.setImageUrl(getFullImageUrl(product.getImageUrl()));
        response.setCategory(categoryDtoMapper.toResponse(product.getCategory()));
        response.setActive(product.isActive());
        response.setDisplayOrder(product.getDisplayOrder());
        
        logger.debug("DTO de resposta gerado com URL de imagem: {}", response.getImageUrl());
        
        return response;
    }
    
    /**
     * Converte lista de entidades de domínio para lista de DTOs de resposta
     * @param products Lista de entidades de domínio
     * @return Lista de DTOs de resposta
     */
    public List<ProductResponse> toResponseList(List<Product> products) {
        if (products == null) {
            return null;
        }
        
        List<ProductResponse> responses = new ArrayList<>(products.size());
        for (Product product : products) {
            responses.add(toResponse(product));
        }
        
        return responses;
    }
    
    /**
     * Converte DTO de requisição para entidade de domínio
     * @param request DTO de requisição
     * @return Entidade de domínio
     */
    public Product toDomain(ProductRequest request) {
        if (request == null) {
            return null;
        }
        
        Product product = new Product();
        updateDomainFromRequest(request, product);
        product.setActive(true);
        
        return product;
    }
    
    /**
     * Atualiza uma entidade de domínio com os dados de um DTO de requisição
     * @param request DTO de requisição com os dados atualizados
     * @param product Entidade de domínio a ser atualizada
     */
    public void updateDomainFromRequest(ProductRequest request, Product product) {
        if (request == null || product == null) {
            return;
        }
        
        product.setName(cleanUrl(request.getName()));
        product.setDescription(cleanUrl(request.getDescription()));
        product.setPrice(request.getPrice());
        product.setDisplayOrder(request.getDisplayOrder());

        if (request.getImageUrl() != null && !request.getImageUrl().isEmpty()) {
            product.setImageUrl(stripBaseUrl(request.getImageUrl()));
        }
        
        setCategoryFromId(request, product);
    }
    
    /**
     * Define a categoria de um produto a partir de um ID
     * @param product Produto a ser atualizado
     * @param category Categoria a ser definida
     */
    public void setCategoryFromId(ProductRequest request, Product product) {
        if (request.getCategoryId() != null) {
            Category category = new Category();
            category.setId(request.getCategoryId());
            product.setCategory(category);
        }
    }
    
    /**
     * Obtém a URL completa da imagem a partir do caminho relativo
     * @param imagePath Caminho relativo da imagem
     * @return URL completa da imagem
     */
    protected String getFullImageUrl(String imagePath) {
        if (imagePath == null || imagePath.isEmpty()) {
            return null;
        }

        if (imagePath.startsWith("http://") || imagePath.startsWith("https://")) {
            return imagePath;
        }
        
        return imageStorageService.getImageUrl(imagePath);
    }
    
    /**
     * Remove URLs de um texto
     * @param text Texto a ser limpo
     * @return Texto sem URLs
     */
    protected String cleanUrl(String text) {
        if (text == null) {
            return null;
        }
        return text;
    }
    
    /**
     * Remove o baseUrl de uma URL completa, mantendo apenas o caminho relativo
     * @param imageUrl URL completa da imagem
     * @return Caminho relativo da imagem
     */
    protected String stripBaseUrl(String imageUrl) {
        if (imageUrl == null) {
            return null;
        }
        if (imageUrl.startsWith(cdnBaseUrl)) {
            return imageUrl.substring(cdnBaseUrl.length() + 1);
        }
        return imageUrl;
    }
} 