package br.com.fiap.postech.products.application.gateway.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.fiap.postech.products.domain.entity.Product;
import br.com.fiap.postech.products.infrastructure.gateway.ProductRepositoryGateway;
import br.com.fiap.postech.products.infrastructure.mapper.ProductMapper;
import br.com.fiap.postech.products.model.ProductApiModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

class UpdateProductGatewayImplTest {

    private UpdateProductGatewayImpl updateProductGateway;
    private ProductRepositoryGateway productRepositoryGateway;
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productRepositoryGateway = mock(ProductRepositoryGateway.class);
        productMapper = mock(ProductMapper.class);
        updateProductGateway = new UpdateProductGatewayImpl(productRepositoryGateway, productMapper);
    }

    @Test
    void updateProduct_ProductExists_UpdatesAndReturnsProduct() {
        Long productId = 1L;
        ProductApiModel productApiModel = new ProductApiModel();
        productApiModel.setName("Updated Name");
        productApiModel.setDescription("Updated Description");
        productApiModel.setPrice(new BigDecimal("100"));

        Product product = new Product();
        when(productRepositoryGateway.findById(productId)).thenReturn(product);
        when(productRepositoryGateway.save(product)).thenReturn(product);
        when(productMapper.modelToDTO(product)).thenReturn(productApiModel);

        ProductApiModel result = updateProductGateway.updateProduct(productId, productApiModel);

        assertEquals(productApiModel, result);
    }

    @Test
    void updateProduct_ProductDoesNotExist_ThrowsException() {
        Long productId = 1L;
        ProductApiModel productApiModel = new ProductApiModel();
        when(productRepositoryGateway.findById(productId)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> updateProductGateway.updateProduct(productId, productApiModel));
    }
}