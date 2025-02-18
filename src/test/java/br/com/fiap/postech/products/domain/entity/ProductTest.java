package br.com.fiap.postech.products.domain.entity;

import br.com.fiap.postech.products.exception.InvalidAttributeException;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductTest {

    @Test
    void updateName_ValidName_UpdatesName() {
        Product product = new Product();
        product.updateName("New Product Name");
        assertEquals("New Product Name", product.getName());
    }

    @Test
    void updateName_NullName_ThrowsInvalidAttributeException() {
        Product product = new Product();
        assertThrows(InvalidAttributeException.class, () -> product.updateName(null));
    }

    @Test
    void updateName_EmptyName_ThrowsInvalidAttributeException() {
        Product product = new Product();
        assertThrows(InvalidAttributeException.class, () -> product.updateName(""));
    }

    @Test
    void updateDescription_ValidDescription_UpdatesDescription() {
        Product product = new Product();
        product.updateDescription("New Product Description");
        assertEquals("New Product Description", product.getDescription());
    }

    @Test
    void updateDescription_NullDescription_ThrowsInvalidAttributeException() {
        Product product = new Product();
        assertThrows(InvalidAttributeException.class, () -> product.updateDescription(null));
    }

    @Test
    void updateDescription_EmptyDescription_ThrowsInvalidAttributeException() {
        Product product = new Product();
        assertThrows(InvalidAttributeException.class, () -> product.updateDescription(""));
    }

    @Test
    void updatePrice_ValidPrice_UpdatesPrice() {
        Product product = new Product();
        product.updatePrice(BigDecimal.valueOf(100.00));
        assertEquals(BigDecimal.valueOf(100.00), product.getPrice());
    }

    @Test
    void updatePrice_NullPrice_ThrowsInvalidAttributeException() {
        Product product = new Product();
        assertThrows(InvalidAttributeException.class, () -> product.updatePrice(null));
    }

    @Test
    void updatePrice_NegativePrice_ThrowsInvalidAttributeException() {
        Product product = new Product();
        BigDecimal negativePrice = BigDecimal.valueOf(-1.00);

        assertThrows(InvalidAttributeException.class, () -> product.updatePrice(negativePrice));
    }

    @Test
    void updateStockQuantity_ValidStockQuantity_UpdatesStockQuantity() {
        Product product = new Product();
        product.updateStockQuantity(10);
        assertEquals(10, product.getStockQuantity());
    }

    @Test
    void updateStockQuantity_NullStockQuantity_ThrowsInvalidAttributeException() {
        Product product = new Product();
        assertThrows(InvalidAttributeException.class, () -> product.updateStockQuantity(null));
    }

    @Test
    void updateStockQuantity_NegativeStockQuantity_ThrowsInvalidAttributeException() {
        Product product = new Product();
        assertThrows(InvalidAttributeException.class, () -> product.updateStockQuantity(-1));
    }
}