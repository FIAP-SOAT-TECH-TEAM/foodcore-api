package com.soat.fiap.food.core.api.shared.infrastructure.storage;

import org.springframework.web.multipart.MultipartFile;

/**
 * Interface para serviço de armazenamento de imagens
 */
public interface ImageStorageService {
    
    /**
     * Faz upload de uma imagem para o armazenamento
     * 
     * @param file Arquivo da imagem a ser armazenada
     * @param path Caminho relativo dentro do armazenamento
     * @return URL completa para acesso à imagem
     */
    String uploadImage(MultipartFile file, String path);
    
    /**
     * Remove uma imagem do armazenamento
     * 
     * @param imagePath Caminho da imagem a ser removida
     */
    void deleteImage(String imagePath);
    
    /**
     * Obtém a URL completa para um caminho de imagem armazenado
     * 
     * @param imagePath Caminho relativo da imagem
     * @return URL completa para acesso à imagem
     */
    String getImageUrl(String imagePath);
} 