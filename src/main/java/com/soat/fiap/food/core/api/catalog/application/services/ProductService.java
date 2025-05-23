//package com.soat.fiap.food.core.api.catalog.application.services;
//
//import com.soat.fiap.food.core.api.catalog.application.ports.in.ProductUseCase;
//import com.soat.fiap.food.core.api.catalog.domain.events.ProductCreatedEvent;
//import com.soat.fiap.food.core.api.catalog.domain.model.Product;
//import com.soat.fiap.food.core.api.catalog.application.dto.request.ProductRequest;
//import com.soat.fiap.food.core.api.catalog.application.dto.response.ProductResponse;
//import com.soat.fiap.food.core.api.catalog.application.mapper.ProductDtoMapper;
//import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
//import com.soat.fiap.food.core.api.shared.infrastructure.storage.ImageStorageService;
//
//import org.springframework.context.ApplicationEventPublisher;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//import org.springframework.web.multipart.MultipartFile;
//
//import java.util.List;
//import java.util.Optional;
//
///**
// * Implementação do caso de uso de Produto
// */
//@Service
//public class ProductService implements ProductUseCase {
//
//    private final ProductRepository productRepository;
//    private final ApplicationEventPublisher eventPublisher;
//    private final ProductDtoMapper productDtoMapper;
//    private final CustomLogger logger;
//    private final ImageStorageService imageStorageService;
//
//    public ProductService(
//            ProductRepository productRepository,
//            ApplicationEventPublisher eventPublisher,
//            ProductDtoMapper productDtoMapper,
//            ImageStorageService imageStorageService) {
//        this.productRepository = productRepository;
//        this.eventPublisher = eventPublisher;
//        this.productDtoMapper = productDtoMapper;
//        this.imageStorageService = imageStorageService;
//        this.logger = CustomLogger.getLogger(getClass());
//    }
//
//    @Override
//    @Transactional
//    public Product createProduct(Product product) {
//        logger.debug("Criando produto: {}", product);
//        product.activate();
//        Product savedProduct = productRepository.save(product);
//        logger.debug("Produto criado com sucesso: {}", savedProduct);
//
//        ProductCreatedEvent event = ProductCreatedEvent.of(
//                savedProduct.getId(),
//                savedProduct.getName(),
//                savedProduct.getPrice(),
//                savedProduct.getCategory().getId()
//        );
//        eventPublisher.publishEvent(event);
//        logger.debug("Evento de produto criado publicado: {}", event);
//
//        return savedProduct;
//    }
//
//    @Override
//    @Transactional
//    public Product updateProduct(Long id, Product product) {
//        logger.debug("Atualizando produto com ID {}: {}", id, product);
//        Optional<Product> existingProduct = productRepository.findById(id);
//
//        if (existingProduct.isEmpty()) {
//            logger.warn("Produto não encontrado com ID: {}", id);
//            throw new RuntimeException("Produto não encontrado com ID: " + id);
//        }
//
//        product.setId(id);
//        Product updatedProduct = productRepository.save(product);
//        logger.debug("Produto atualizado com sucesso: {}", updatedProduct);
//        return updatedProduct;
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public Optional<Product> getProductById(Long id) {
//        logger.debug("Buscando produto com ID: {}", id);
//        Optional<Product> product = productRepository.findById(id);
//        if (product.isPresent()) {
//            logger.debug("Produto encontrado: {}", product.get());
//        } else {
//            logger.debug("Produto não encontrado com ID: {}", id);
//        }
//        return product;
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<Product> getAllProducts() {
//        logger.debug("Buscando todos os produtos");
//        List<Product> products = productRepository.findAll();
//        logger.debug("Encontrados {} produtos", products.size());
//        return products;
//    }
//
//    @Override
//    @Transactional(readOnly = true)
//    public List<Product> getProductsByCategory(Long categoryId) {
//        logger.debug("Buscando produtos da categoria ID: {}", categoryId);
//        List<Product> products = productRepository.findByCategory(categoryId);
//        logger.debug("Encontrados {} produtos na categoria ID: {}", products.size(), categoryId);
//        return products;
//    }
//
//    @Override
//    @Transactional
//    public void deleteProduct(Long id) {
//        logger.debug("Removendo produto com ID: {}", id);
//        productRepository.delete(id);
//        logger.debug("Produto removido com sucesso, ID: {}", id);
//    }
//
//    /**
//     * Cria um produto a partir de um DTO de requisição
//     * @param request DTO com dados do produto
//     * @return Produto criado
//     */
//    @Transactional
//    public Product createProductFromRequest(ProductRequest request) {
//        logger.debug("Convertendo request para produto: {}", request);
//        Product product = productDtoMapper.toDomain(request);
//        logger.debug("Produto convertido: {}", product);
//        Product savedProduct = createProduct(product);
//
//        return getProductById(savedProduct.getId()).orElse(savedProduct);
//    }
//
//    /**
//     * Atualiza um produto a partir de um DTO de requisição
//     * @param id ID do produto
//     * @param request DTO com dados atualizados
//     * @return Produto atualizado ou empty se não encontrado
//     */
//    @Transactional
//    public Optional<Product> updateProductFromRequest(Long id, ProductRequest request) {
//        logger.debug("Atualizando produto ID {} com request: {}", id, request);
//        return getProductById(id).map(existingProduct -> {
//            logger.debug("Produto encontrado para atualização: {}", existingProduct);
//            productDtoMapper.updateDomainFromRequest(request, existingProduct);
//            productDtoMapper.setCategoryFromId(request, existingProduct);
//            logger.debug("Produto atualizado com dados do request: {}", existingProduct);
//
//            return updateProduct(id, existingProduct);
//        });
//    }
//
//    /**
//     * Converte um produto para seu DTO de resposta
//     * @param product Produto a ser convertido
//     * @return DTO de resposta
//     */
//    public ProductResponse toResponse(Product product) {
//        return productDtoMapper.toResponse(product);
//    }
//
//    /**
//     * Converte uma lista de produtos para uma lista de DTOs de resposta
//     * @param products Lista de produtos a ser convertida
//     * @return Lista de DTOs de resposta
//     */
//    public List<ProductResponse> toResponseList(List<Product> products) {
//        return productDtoMapper.toResponseList(products);
//    }
//
//    /**
//     * Cria um produto com imagem
//     * @param productRequest Dados do produto
//     * @param imageFile Arquivo de imagem (opcional)
//     * @return Produto criado
//     */
//    @Transactional
//    public Product createProductWithImage(ProductRequest productRequest, MultipartFile imageFile) {
//        logger.debug("Criando produto com possível imagem: {}", productRequest.getName());
//        Product product = createProductFromRequest(productRequest);
//        if (imageFile != null && !imageFile.isEmpty()) {
//            String imagePath = uploadProductImage(product.getId(), imageFile);
//            product.setImageUrl(imagePath);
//            product = updateProduct(product.getId(), product);
//
//            return getProductById(product.getId()).orElse(product);
//        }
//
//        return product;
//    }
//
//    /**
//     * Atualiza apenas a imagem de um produto existente
//     * @param id ID do produto
//     * @param imageFile Arquivo da nova imagem
//     * @return Produto atualizado com a nova imagem
//     */
//    @Transactional
//    public Product updateProductImage(Long id, MultipartFile imageFile) {
//        logger.debug("Atualizando imagem do produto ID: {}", id);
//
//        Product existingProduct = getProductById(id)
//                .orElseThrow(() -> new RuntimeException("Produto não encontrado com ID: " + id));
//
//        String imagePath = uploadProductImage(id, imageFile);
//        existingProduct.setImageUrl(imagePath);
//
//        return updateProduct(id, existingProduct);
//    }
//
//    /**
//     * Faz upload de uma imagem para um produto, removendo a anterior se existir
//     * @param productId ID do produto
//     * @param imageFile Arquivo da imagem
//     * @return Caminho da imagem no storage
//     */
//    private String uploadProductImage(Long productId, MultipartFile imageFile) {
//        if (imageFile == null || imageFile.isEmpty()) {
//            logger.warn("Tentativa de upload de imagem com arquivo vazio ou nulo");
//            throw new IllegalArgumentException("O arquivo de imagem não pode ser vazio");
//        }
//
//        logger.debug("Processando upload de imagem para produto ID: {}", productId);
//
//        try {
//            Optional<Product> productOpt = getProductById(productId);
//
//            if (productOpt.isPresent() && productOpt.get().getImageUrl() != null && !productOpt.get().getImageUrl().isEmpty()) {
//                String currentImagePath = productOpt.get().getImageUrl();
//                logger.debug("Removendo imagem anterior: {}", currentImagePath);
//                imageStorageService.deleteImage(currentImagePath);
//            }
//
//            String storagePath = "products/" + productId;
//            String imagePath = imageStorageService.uploadImage(imageFile, storagePath);
//            logger.debug("Nova imagem enviada para o caminho: {}", imagePath);
//
//            return imagePath;
//        } catch (Exception e) {
//            logger.error("Erro ao processar upload de imagem: {}", e.getMessage(), e);
//            throw new RuntimeException("Falha ao processar imagem: " + e.getMessage(), e);
//        }
//    }
//}