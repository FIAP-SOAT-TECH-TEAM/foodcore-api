package com.soat.fiap.food.core.api.product.domain.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Entidade de domínio que representa um produto
 * AGGREGATE ROOT:
 *  - Toda modificação de entidades internas do agregado devem passar pela entidade raíz;
 *  - Único ponto de entrada para qualquer entidade interna do agregado (Lei de Demeter);
 *  - Entidades dentro deste agregado podem se referenciar via id ou objeto;
 *  - Entidades de outros agregados só podem referenciar esta entidade raiz, e isto deve ser via Id;
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {
    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private String imageUrl;
    private Category category;
    /**
     * -- GETTER --
     *  Verifica se o produto está ativo
     *
     * @return true se ativo, false caso contrário
     */
    @Getter
    private boolean active;
    private Integer displayOrder;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    
    /**
     * Ativa o produto
     */
    public void activate() {
        this.active = true;
    }
    
    /**
     * Desativa o produto
     */
    public void deactivate() {
        this.active = false;
    }

    /**
     * Verifica se o produto está disponível para venda.
     * 
     * @return true se o produto estiver ativo e com preço válido.
     */
    public boolean isAvailable() {
        return active && price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }
} 