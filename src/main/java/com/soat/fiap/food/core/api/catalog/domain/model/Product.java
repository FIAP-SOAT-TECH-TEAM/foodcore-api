package com.soat.fiap.food.core.api.catalog.domain.model;

import com.soat.fiap.food.core.api.catalog.domain.exceptions.CatalogException;
import com.soat.fiap.food.core.api.catalog.domain.exceptions.ProductException;
import com.soat.fiap.food.core.api.catalog.domain.vo.Details;
import com.soat.fiap.food.core.api.catalog.domain.vo.ImageUrl;
import com.soat.fiap.food.core.api.order.domain.model.OrderItem;
import com.soat.fiap.food.core.api.order.domain.vo.OrderNumber;
import com.soat.fiap.food.core.api.order.domain.vo.OrderStatus;
import lombok.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

import com.soat.fiap.food.core.api.shared.vo.AuditInfo;
import org.apache.commons.lang3.Validate;

/**
 * Entidade de domínio que representa um produto
 */
@Getter(AccessLevel.PACKAGE)
@Setter(AccessLevel.PACKAGE)
public class Product {

    private Long id;
    private Details details;
    private BigDecimal price;
    private ImageUrl imageUrl;
    private boolean active = true;
    private Integer displayOrder;
    private final AuditInfo auditInfo = new AuditInfo();

    private Category category;
    private final Stock stock = new Stock();

    public Product(
            Details details,
            BigDecimal price,
            ImageUrl imageUrl,
            boolean active,
            Integer displayOrder

    ) {
        validate(details, price, displayOrder);
        this.details = details;
        this.price = price;
        this.imageUrl = imageUrl;
        this.active = active;
        this.displayOrder = displayOrder;
    }

    private void validate(
            Details details,
            BigDecimal price,
            Integer displayOrder
    ) {
        Objects.requireNonNull(details, "Os detalhes do produto não podem ser nulos");
        Objects.requireNonNull(price, "O preço do produto não pode ser nulo");

        if (displayOrder != null && displayOrder < 0) {
            throw new CatalogException("A ordem de exibição da categoria deve ser maior que 0");
        }
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductException("O preço deve ser maior que 0");
        }
    }

    String getName() {
        return this.details.name();
    }

    String getDescription() {
        return this.details.description();
    }


    void setPrice(BigDecimal price) {
        if (price.compareTo(BigDecimal.ZERO) < 0) {
            throw new ProductException("O preço deve ser maior que 0");
        }

        this.price = price;
    }

    void updateStockQuantity (int quantity) {
        stock.setQuantity(quantity);
    }

    /**
     * Verifica se o produto está disponível para venda.
     * 
     * @return true se o produto estiver ativo e com preço válido.
     */
    boolean isActive() {
        return active && price != null && price.compareTo(BigDecimal.ZERO) > 0;
    }
} 