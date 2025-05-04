package com.soat.fiap.food.core.api.shared.infrastructure.storage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ImageStorageServiceImpl implements ImageStorageService {
    
    @Value("${app.cdn.base-url}")
    private String cdnBaseUrl;
    
    @Value("${app.cdn.storage-directory}")
    private String storageDirectory;
    
    @Override
    public String uploadImage(MultipartFile file, String path) {
        try {
            String originalFilename = file.getOriginalFilename();
            String fileExtension = getFileExtension(originalFilename);
            String filename = UUID.randomUUID().toString() + fileExtension;
            String relativePath = path + "/" + filename;
            
            Path targetPath = Paths.get(storageDirectory, path);
            Files.createDirectories(targetPath);
            
            Path fullTargetPath = targetPath.resolve(filename);
            Files.copy(file.getInputStream(), fullTargetPath, StandardCopyOption.REPLACE_EXISTING);
            
            log.info("Imagem armazenada em: {}", fullTargetPath);
            return relativePath;
        } catch (IOException e) {
            log.error("Falha ao armazenar imagem", e);
            throw new RuntimeException("Falha ao armazenar imagem: " + e.getMessage(), e);
        }
    }
    
    @Override
    public void deleteImage(String imagePath) {
        try {
            Path targetPath = Paths.get(storageDirectory, imagePath);
            Files.deleteIfExists(targetPath);
            log.info("Imagem removida: {}", targetPath);
        } catch (IOException e) {
            log.error("Falha ao remover imagem", e);
            throw new RuntimeException("Falha ao remover imagem: " + e.getMessage(), e);
        }
    }
    
    @Override
    public String getImageUrl(String imagePath) {
        if (imagePath == null) {
            return null;
        }
        
        log.debug("getImageUrl sendo chamado para: {}", imagePath);

        if (imagePath.startsWith(cdnBaseUrl)) {
            log.debug("URL já possui o prefixo, retornando sem alterações: {}", imagePath);
            return imagePath;
        }
        
        String fullUrl = cdnBaseUrl + "/" + imagePath;
        log.debug("URL completa gerada: {}", fullUrl);
        return fullUrl;
    }
    
    private String getFileExtension(String filename) {
        if (filename == null) {
            return ".jpg";
        }
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex < 0) {
            return ".jpg";
        }
        return filename.substring(lastDotIndex);
    }
} 