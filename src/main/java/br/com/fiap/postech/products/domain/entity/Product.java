package br.com.fiap.postech.products.domain.entity;

import br.com.fiap.postech.products.exception.InvalidAttributeException;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class Product {
    private Long id;

    private String name;

    private String description;

    private BigDecimal price;

    private Integer stockQuantity;

    public Product(Long id,String name, String description, BigDecimal price, Integer stockQuantity) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.stockQuantity = stockQuantity;
    }

    public Product(){}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void updateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidAttributeException("Name cannot be null or empty.");
        }
        this.name = name;
    }

    public void updateDescription(String description) {
        if (description == null || description.trim().isEmpty()) {
            throw new InvalidAttributeException("Description cannot be null or empty.");
        }
        this.description = description;
    }

    public void updatePrice(BigDecimal price) {
        if (price == null || price.compareTo(BigDecimal.ZERO) < 0) {
            throw new InvalidAttributeException("Price must be positive.");
        }
        this.price = price;
    }

    public void updateStockQuantity(Integer stockQuantity) {
        if (stockQuantity == null || stockQuantity < 0) {
            throw new InvalidAttributeException("Stock quantity cannot be negative.");
        }
        this.stockQuantity = stockQuantity;
    }

    public void substractStock(@NotNull @Min(1) Integer quantity) {
        if (quantity > stockQuantity) {
            throw new InvalidAttributeException("Stock quantity cannot be negative.");
        }
        stockQuantity -= quantity;
    }
}
