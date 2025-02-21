package br.com.fiap.postech.products.infrastructure.persistence;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductEntityTest {

    @Test
    void createProductEntity_ValidData_CreatesProductEntity() {
        ProductEntity productEntity = new ProductEntity(1L, "Product Name", "Product Description", BigDecimal.valueOf(100.00), 10);

        assertNotNull(productEntity);
        assertEquals(1L, productEntity.getId());
        assertEquals("Product Name", productEntity.getName());
        assertEquals("Product Description", productEntity.getDescription());
        assertEquals(BigDecimal.valueOf(100.00), productEntity.getPrice());
        assertEquals(10, productEntity.getStockQuantity());
    }

    @Test
    void setName_ValidName_UpdatesName() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName("Updated Name");

        assertEquals("Updated Name", productEntity.getName());
    }

    @Test
    void setDescription_ValidDescription_UpdatesDescription() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setDescription("Updated Description");

        assertEquals("Updated Description", productEntity.getDescription());
    }

    @Test
    void setPrice_ValidPrice_UpdatesPrice() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setPrice(BigDecimal.valueOf(200.00));

        assertEquals(BigDecimal.valueOf(200.00), productEntity.getPrice());
    }

    @Test
    void setStockQuantity_ValidStockQuantity_UpdatesStockQuantity() {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setStockQuantity(20);

        assertEquals(20, productEntity.getStockQuantity());
    }
}