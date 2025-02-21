package br.com.fiap.postech.products.infrastructure.batch.job;

import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.item.file.transform.DefaultFieldSet;
import org.springframework.batch.item.file.transform.FieldSet;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductEntityMapperTest {

    private ProductEntityMapper productEntityMapper;

    @BeforeEach
    void setUp() {
        productEntityMapper = new ProductEntityMapper();
    }

    @Test
    void mapFieldSet_mapsFieldsCorrectly() {
        String[] tokens = {"name", "description", "10.99", "100"};
        String[] names = {"name", "description", "price", "stockQuantity"};
        FieldSet fieldSet = new DefaultFieldSet(tokens, names);

        ProductEntity productEntity = productEntityMapper.mapFieldSet(fieldSet);

        assertEquals("name", productEntity.getName());
        assertEquals("description", productEntity.getDescription());
        assertEquals(new BigDecimal("10.99"), productEntity.getPrice());
        assertEquals(100, productEntity.getStockQuantity());
    }

    @Test
    void mapFieldSet_throwsExceptionWhenFieldSetIsNull() {
        assertThrows(NullPointerException.class, () -> productEntityMapper.mapFieldSet(null));
    }

    @Test
    void mapFieldSet_throwsExceptionWhenPriceIsInvalid() {
        String[] tokens = {"name", "description", "invalidPrice", "100"};
        String[] names = {"name", "description", "price", "stockQuantity"};
        FieldSet fieldSet = new DefaultFieldSet(tokens, names);

        assertThrows(NumberFormatException.class, () -> productEntityMapper.mapFieldSet(fieldSet));
    }

    @Test
    void mapFieldSet_throwsExceptionWhenStockQuantityIsInvalid() {
        String[] tokens = {"name", "description", "10.99", "invalidQuantity"};
        String[] names = {"name", "description", "price", "stockQuantity"};
        FieldSet fieldSet = new DefaultFieldSet(tokens, names);

        assertThrows(NumberFormatException.class, () -> productEntityMapper.mapFieldSet(fieldSet));
    }
}