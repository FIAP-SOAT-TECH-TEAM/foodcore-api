package com.soat.fiap.food.core.api.product.application.services;

import com.soat.fiap.food.core.api.product.application.ports.in.ProductUseCase;
import com.soat.fiap.food.core.api.product.application.ports.out.ProductRepository;
import com.soat.fiap.food.core.api.product.domain.events.ProductCreatedEvent;
import com.soat.fiap.food.core.api.product.domain.model.Product;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.in.dto.request.ProductRequest;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.in.dto.response.ProductResponse;
import com.soat.fiap.food.core.api.product.mapper.ProductDtoMapper;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Implementação do caso de uso de Produto
 */
@Service
@Slf4j
public class ProductService implements ProductUseCase {

    private final ProductRepository productRepository;
    private final ApplicationEventPublisher eventPublisher;
    private final ProductDtoMapper productDtoMapper;

    public ProductService(
            ProductRepository productRepository, 
            ApplicationEventPublisher eventPublisher,
            ProductDtoMapper productDtoMapper) {
        this.productRepository = productRepository;
        this.eventPublisher = eventPublisher;
        this.productDtoMapper = productDtoMapper;
    }

    @Override
    @Transactional
    public Product createProduct(Product product) {
        log.debug("Criando produto: {}", product);
        product.activate();
        Product savedProduct = productRepository.save(product);
        log.debug("Produto criado com sucesso: {}", savedProduct);

        ProductCreatedEvent event = ProductCreatedEvent.of(
                savedProduct.getId(),
                savedProduct.getName(),
                savedProduct.getPrice(),
                savedProduct.getCategory().getId()
        );
        eventPublisher.publishEvent(event);
        log.debug("Evento de produto criado publicado: {}", event);
        
        return savedProduct;
    }

    @Override
    @Transactional
    public Product updateProduct(Long id, Product product) {
        log.debug("Atualizando produto com ID {}: {}", id, product);
        Optional<Product> existingProduct = productRepository.findById(id);
        
        if (existingProduct.isEmpty()) {
            log.warn("Produto não encontrado com ID: {}", id);
            throw new RuntimeException("Produto não encontrado com ID: " + id);
        }
        
        product.setId(id);
        Product updatedProduct = productRepository.save(product);
        log.debug("Produto atualizado com sucesso: {}", updatedProduct);
        return updatedProduct;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Product> getProductById(Long id) {
        log.debug("Buscando produto com ID: {}", id);
        Optional<Product> product = productRepository.findById(id);
        if (product.isPresent()) {
            log.debug("Produto encontrado: {}", product.get());
        } else {
            log.debug("Produto não encontrado com ID: {}", id);
        }
        return product;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getAllProducts() {
        log.debug("Buscando todos os produtos");
        List<Product> products = productRepository.findAll();
        log.debug("Encontrados {} produtos", products.size());
        return products;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Product> getProductsByCategory(Long categoryId) {
        log.debug("Buscando produtos da categoria ID: {}", categoryId);
        List<Product> products = productRepository.findByCategory(categoryId);
        log.debug("Encontrados {} produtos na categoria ID: {}", products.size(), categoryId);
        return products;
    }

    @Override
    @Transactional
    public void deleteProduct(Long id) {
        log.debug("Removendo produto com ID: {}", id);
        productRepository.delete(id);
        log.debug("Produto removido com sucesso, ID: {}", id);
    }
    
    /**
     * Cria um produto a partir de um DTO de requisição
     * @param request DTO com dados do produto
     * @return Produto criado
     */
    @Transactional
    public Product createProductFromRequest(ProductRequest request) {
        log.debug("Convertendo request para produto: {}", request);
        Product product = productDtoMapper.toDomain(request);
        log.debug("Produto convertido: {}", product);
        return createProduct(product);
    }
    
    /**
     * Atualiza um produto a partir de um DTO de requisição
     * @param id ID do produto
     * @param request DTO com dados atualizados
     * @return Produto atualizado ou empty se não encontrado
     */
    @Transactional
    public Optional<Product> updateProductFromRequest(Long id, ProductRequest request) {
        log.debug("Atualizando produto ID {} com request: {}", id, request);
        return getProductById(id).map(existingProduct -> {
            log.debug("Produto encontrado para atualização: {}", existingProduct);
            productDtoMapper.updateDomainFromRequest(request, existingProduct);
            productDtoMapper.setCategoryFromId(request, existingProduct);
            log.debug("Produto atualizado com dados do request: {}", existingProduct);
            return updateProduct(id, existingProduct);
        });
    }
    
    /**
     * Converte um produto para seu DTO de resposta
     * @param product Produto a ser convertido
     * @return DTO de resposta
     */
    public ProductResponse toResponse(Product product) {
        log.debug("Convertendo produto para response: {}", product);
        ProductResponse response = productDtoMapper.toResponse(product);
        log.debug("Response gerada: {}", response);
        return response;
    }
    
    /**
     * Converte uma lista de produtos para lista de DTOs de resposta
     * @param products Lista de produtos
     * @return Lista de DTOs de resposta
     */
    public List<ProductResponse> toResponseList(List<Product> products) {
        log.debug("Convertendo lista de {} produtos para response", products.size());
        return productDtoMapper.toResponseList(products);
    }
} 