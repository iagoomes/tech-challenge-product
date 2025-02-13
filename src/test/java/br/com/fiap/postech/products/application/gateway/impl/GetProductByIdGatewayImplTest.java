package br.com.fiap.postech.products.application.gateway.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.fiap.postech.products.domain.entity.Product;
import br.com.fiap.postech.products.infrastructure.gateway.ProductRepositoryGateway;
import br.com.fiap.postech.products.infrastructure.mapper.ProductMapper;
import br.com.fiap.postech.products.model.ProductApiModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetProductByIdGatewayImplTest {

    private GetProductByIdGatewayImpl getProductByIdGateway;
    private ProductRepositoryGateway productRepositoryGateway;
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productRepositoryGateway = mock(ProductRepositoryGateway.class);
        productMapper = mock(ProductMapper.class);
        getProductByIdGateway = new GetProductByIdGatewayImpl(productRepositoryGateway, productMapper);
    }

    @Test
    void getProductById_ProductExists_ReturnsProduct() {
        Long productId = 1L;
        Product product = new Product();
        ProductApiModel productApiModel = new ProductApiModel();
        when(productRepositoryGateway.findById(productId)).thenReturn(product);
        when(productMapper.modelToDTO(product)).thenReturn(productApiModel);

        ProductApiModel result = getProductByIdGateway.getProductById(productId);

        assertEquals(productApiModel, result);
    }

    @Test
    void getProductById_ProductDoesNotExist_ReturnsNull() {
        Long productId = 1L;
        when(productRepositoryGateway.findById(productId)).thenReturn(null);

        ProductApiModel result = getProductByIdGateway.getProductById(productId);

        assertNull(result);
    }
}