package br.com.fiap.postech.products.infrastructure.mapper;

import br.com.fiap.postech.products.domain.entity.Product;
import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import br.com.fiap.postech.products.model.ProductApiModel;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class ProductMapperTest {

    private final ProductMapper productMapper = new ProductMapper();

    @Test
    void toEntity_ValidProduct_ReturnsProductEntity() {
        Product product = new Product(1L, "Product Name", "Product Description", BigDecimal.valueOf(100.00), 10);
        ProductEntity productEntity = productMapper.toEntity(product);

        assertNotNull(productEntity);
        assertEquals(product.getId(), productEntity.getId());
        assertEquals(product.getName(), productEntity.getName());
        assertEquals(product.getDescription(), productEntity.getDescription());
        assertEquals(product.getPrice(), productEntity.getPrice());
        assertEquals(product.getStockQuantity(), productEntity.getStockQuantity());
    }

    @Test
    void toModel_ValidProductEntity_ReturnsProduct() {
        ProductEntity productEntity = new ProductEntity(1L, "Product Name", "Product Description", BigDecimal.valueOf(100.00), 10);
        Product product = productMapper.toModel(productEntity);

        assertNotNull(product);
        assertEquals(productEntity.getId(), product.getId());
        assertEquals(productEntity.getName(), product.getName());
        assertEquals(productEntity.getDescription(), product.getDescription());
        assertEquals(productEntity.getPrice(), product.getPrice());
        assertEquals(productEntity.getStockQuantity(), product.getStockQuantity());
    }

    @Test
    void DTOToModel_ValidProductApiModel_ReturnsProduct() {
        ProductApiModel dto = new ProductApiModel();
        dto.setName("Product Name");
        dto.setDescription("Product Description");
        dto.setPrice(BigDecimal.valueOf(100.00));
        dto.setStockQuantity(10);

        Product product = productMapper.dtoToModel(dto);

        assertNotNull(product);
        assertEquals(dto.getName(), product.getName());
        assertEquals(dto.getDescription(), product.getDescription());
        assertEquals(dto.getPrice(), product.getPrice());
        assertEquals(dto.getStockQuantity(), product.getStockQuantity());
    }

    @Test
    void modelToDTO_ValidProduct_ReturnsProductApiModel() {
        Product product = new Product(1L, "Product Name", "Product Description", BigDecimal.valueOf(100.00), 10);
        ProductApiModel dto = productMapper.modelToDTO(product);

        assertNotNull(dto);
        assertEquals(product.getId(), dto.getId());
        assertEquals(product.getName(), dto.getName());
        assertEquals(product.getDescription(), dto.getDescription());
        assertEquals(product.getPrice(), dto.getPrice());
        assertEquals(product.getStockQuantity(), dto.getStockQuantity());
    }
}