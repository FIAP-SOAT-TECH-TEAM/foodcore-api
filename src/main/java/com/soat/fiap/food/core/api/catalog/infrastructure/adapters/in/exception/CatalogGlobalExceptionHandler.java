package com.soat.fiap.food.core.api.catalog.infrastructure.adapters.in.exception;

import com.soat.fiap.food.core.api.catalog.infrastructure.adapters.in.controller.CatalogController;
import com.soat.fiap.food.core.api.shared.exception.*;
import com.soat.fiap.food.core.api.shared.infrastructure.logging.CustomLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.annotation.Order;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;

/**
 * Handler global de exceções para o catalogo
 */
@RestControllerAdvice(assignableTypes = {CatalogController.class})
@Order(1)
public class CatalogGlobalExceptionHandler {

    private final CustomLogger logger = CustomLogger.getLogger(getClass());

    /**
     * Trata erros de integridade de dados (ex: violação de chave estrangeira)
     */
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponse> handleDataIntegrityViolationException(
            DataIntegrityViolationException ex, HttpServletRequest request) {
        
        logger.error("Erro de integridade de dados: {}", ex.getMessage());
        
        String mensagem = ex.getMessage();
        
        if (mensagem != null && mensagem.contains("fk_category_catalog")) {
            mensagem = "Não é possível excluir este catalogo porque existem categorias associados a ele";
        }
        
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.CONFLICT.value(),
                mensagem,
                request.getRequestURI()
        );
        
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }
} 