package com.soat.fiap.food.core.api.product.application.services;

import com.soat.fiap.food.core.api.product.application.ports.in.CategoryUseCase;
import com.soat.fiap.food.core.api.product.application.ports.out.CategoryRepository;
import com.soat.fiap.food.core.api.product.application.ports.out.ProductRepository;
import com.soat.fiap.food.core.api.product.domain.model.Category;
import com.soat.fiap.food.core.api.product.domain.model.Product;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.in.dto.request.CategoryRequest;
import com.soat.fiap.food.core.api.product.infrastructure.adapters.in.dto.response.CategoryResponse;
import com.soat.fiap.food.core.api.product.mapper.CategoryDtoMapper;
import com.soat.fiap.food.core.api.shared.exception.BusinessException;
import com.soat.fiap.food.core.api.shared.exception.ResourceNotFoundException;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import com.soat.fiap.food.core.api.shared.infrastructure.storage.ImageStorageService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

/**
 * Implementação do caso de uso de Categoria
 */
@Service
public class CategoryService implements CategoryUseCase {

    private final CategoryRepository categoryRepository;
    private final ProductRepository productRepository;
    private final CategoryDtoMapper categoryDtoMapper;
    private final ImageStorageService imageStorageService;
    private final CustomLogger logger;

    public CategoryService(
            CategoryRepository categoryRepository,
            ProductRepository productRepository,
            CategoryDtoMapper categoryDtoMapper,
            ImageStorageService imageStorageService) {
        this.categoryRepository = categoryRepository;
        this.productRepository = productRepository;
        this.categoryDtoMapper = categoryDtoMapper;
        this.imageStorageService = imageStorageService;
        this.logger = CustomLogger.getLogger(getClass());
    }

    @Override
    @Transactional
    public Category createCategory(Category category) {
        logger.debug("Criando categoria: {}", category.getName());
        category.activate();
        Category savedCategory = categoryRepository.save(category);
        logger.debug("Categoria criada com sucesso. ID: {}", savedCategory.getId());
        return savedCategory;
    }

    @Override
    @Transactional
    public Category updateCategory(Long id, Category category) {
        logger.debug("Atualizando categoria ID {}: {}", id, category.getName());
        
        Optional<Category> existingCategory = categoryRepository.findById(id);
        if (existingCategory.isEmpty()) {
            logger.warn("Categoria não encontrada com ID: {}", id);
            throw new ResourceNotFoundException("Categoria", "id", id);
        }
        
        category.setId(id);
        Category updatedCategory = categoryRepository.save(category);
        logger.debug("Categoria atualizada com sucesso. ID: {}", updatedCategory.getId());
        
        return updatedCategory;
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Category> getCategoryById(Long id) {
        logger.debug("Buscando categoria com ID: {}", id);
        Optional<Category> category = categoryRepository.findById(id);
        if (category.isPresent()) {
            logger.debug("Categoria encontrada: {}", category.get().getName());
        } else {
            logger.debug("Categoria não encontrada com ID: {}", id);
        }
        return category;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Category> getAllCategories() {
        logger.debug("Buscando todas as categorias");
        List<Category> categories = categoryRepository.findAll();
        logger.debug("Encontradas {} categorias", categories.size());
        return categories;
    }

    @Override
    @Transactional
    public void deleteCategory(Long id) {
        logger.debug("Removendo categoria com ID: {}", id);
        
        Optional<Category> categoryOpt = categoryRepository.findById(id);
        if (categoryOpt.isEmpty()) {
            logger.warn("Tentativa de excluir categoria inexistente. ID: {}", id);
            throw new ResourceNotFoundException("Categoria", "id", id);
        }
        
        List<Product> productsInCategory = productRepository.findByCategory(id);
        if (!productsInCategory.isEmpty()) {
            logger.warn("Não é possível excluir categoria ID {} porque existem {} produtos associados", 
                id, productsInCategory.size());
            throw new BusinessException(
                "Não é possível excluir esta categoria porque existem produtos associados a ela. " +
                "Remova ou reclassifique os produtos antes de excluir a categoria."
            );
        }
        
        categoryRepository.delete(id);
        logger.debug("Categoria removida com sucesso, ID: {}", id);
    }
    
    /**
     * Converte uma categoria para DTO de resposta
     * @param category Categoria a ser convertida
     * @return DTO de resposta
     */
    public CategoryResponse toResponse(Category category) {
        return categoryDtoMapper.toResponse(category);
    }

    /**
     * Converte uma lista de categorias para uma lista de DTOs de resposta
     * @param categories Lista de categorias a ser convertida
     * @return Lista de DTOs de resposta
     */
    public List<CategoryResponse> toResponseList(List<Category> categories) {
        return categoryDtoMapper.toResponseList(categories);
    }
    
    /**
     * Cria uma categoria a partir de um DTO de requisição
     * @param request DTO com dados da categoria
     * @return Categoria criada
     */
    @Transactional
    public Category createCategoryFromRequest(CategoryRequest request) {
        logger.debug("Convertendo request para categoria: {}", request.getName());
        Category category = categoryDtoMapper.toDomain(request);
        logger.debug("Categoria convertida, criando no banco");
        return createCategory(category);
    }
    
    /**
     * Atualiza uma categoria a partir de um DTO de requisição
     * @param id ID da categoria
     * @param request DTO com dados atualizados
     * @return Categoria atualizada ou empty se não encontrada
     */
    @Transactional
    public Optional<Category> updateCategoryFromRequest(Long id, CategoryRequest request) {
        logger.debug("Atualizando categoria ID {} com request: {}", id, request.getName());
        return getCategoryById(id).map(existingCategory -> {
            logger.debug("Categoria encontrada para atualização: {}", existingCategory.getName());
            categoryDtoMapper.updateDomainFromRequest(request, existingCategory);
            logger.debug("Categoria atualizada com dados do request");
            return updateCategory(id, existingCategory);
        });
    }
    
    /**
     * Cria uma categoria com imagem
     * @param categoryRequest Dados da categoria
     * @param imageFile Arquivo de imagem (opcional)
     * @return Categoria criada
     */
    @Transactional
    public Category createCategoryWithImage(CategoryRequest categoryRequest, MultipartFile imageFile) {
        logger.debug("Criando categoria com possível imagem: {}", categoryRequest.getName());
        
        Category category = createCategoryFromRequest(categoryRequest);
        
        if (imageFile != null && !imageFile.isEmpty()) {
            String imagePath = uploadCategoryImage(category.getId(), imageFile);
            category.setImageUrl(imagePath);
            category = updateCategory(category.getId(), category);
        }
        
        return category;
    }
    
    /**
     * Atualiza apenas a imagem de uma categoria existente
     * @param id ID da categoria
     * @param imageFile Arquivo da nova imagem
     * @return Categoria atualizada com a nova imagem
     */
    @Transactional
    public Category updateCategoryImage(Long id, MultipartFile imageFile) {
        logger.debug("Atualizando imagem da categoria ID: {}", id);
        
        Category existingCategory = getCategoryById(id)
                .orElseThrow(() -> new RuntimeException("Categoria não encontrada com ID: " + id));
        
        String imagePath = uploadCategoryImage(id, imageFile);
        existingCategory.setImageUrl(imagePath);
        
        return updateCategory(id, existingCategory);
    }
    
    /**
     * Faz upload de uma imagem para uma categoria, removendo a anterior se existir
     * @param categoryId ID da categoria
     * @param imageFile Arquivo da imagem
     * @return Caminho da imagem no storage
     */
    private String uploadCategoryImage(Long categoryId, MultipartFile imageFile) {
        if (imageFile == null || imageFile.isEmpty()) {
            logger.warn("Tentativa de upload de imagem com arquivo vazio ou nulo");
            throw new IllegalArgumentException("O arquivo de imagem não pode ser vazio");
        }
        
        logger.debug("Processando upload de imagem para categoria ID: {}", categoryId);
        
        try {
            Optional<Category> categoryOpt = getCategoryById(categoryId);
            if (categoryOpt.isPresent() && categoryOpt.get().getImageUrl() != null && !categoryOpt.get().getImageUrl().isEmpty()) {

                String currentImagePath = categoryOpt.get().getImageUrl();
                logger.debug("Removendo imagem anterior: {}", currentImagePath);
                imageStorageService.deleteImage(currentImagePath);
            }
            
            String storagePath = "categories/" + categoryId;
            String imagePath = imageStorageService.uploadImage(imageFile, storagePath);
            logger.debug("Nova imagem enviada para o caminho: {}", imagePath);
            
            return imagePath;
        } catch (Exception e) {
            logger.error("Erro ao processar upload de imagem: {}", e.getMessage(), e);
            throw new RuntimeException("Falha ao processar imagem: " + e.getMessage(), e);
        }
    }
} 