package br.com.fiap.postech.products.application.gateway.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.fiap.postech.products.domain.entity.Product;
import br.com.fiap.postech.products.infrastructure.gateway.ProductRepositoryGateway;
import br.com.fiap.postech.products.infrastructure.mapper.ProductMapper;
import br.com.fiap.postech.products.model.ProductApiModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

class GetAllProductsGatewayImplTest {

    private GetAllProductsGatewayImpl getAllProductsGateway;
    private ProductRepositoryGateway productRepositoryGateway;
    private ProductMapper productMapper;

    @BeforeEach
    void setUp() {
        productRepositoryGateway = mock(ProductRepositoryGateway.class);
        productMapper = mock(ProductMapper.class);
        getAllProductsGateway = new GetAllProductsGatewayImpl(productRepositoryGateway, productMapper);
    }

    @Test
    void getAllProducts_ReturnsProductList() {
        List<ProductApiModel> products = List.of(new ProductApiModel());
        when(productRepositoryGateway.findAll()).thenReturn(List.of(new Product()));
        when(productMapper.modelToDTO(any(Product.class))).thenReturn(new ProductApiModel());

        List<ProductApiModel> result = getAllProductsGateway.getAllProducts();

        assertEquals(products.size(), result.size());
    }

    @Test
    void getAllProducts_EmptyList_ReturnsEmptyList() {
        when(productRepositoryGateway.findAll()).thenReturn(List.of());

        List<ProductApiModel> result = getAllProductsGateway.getAllProducts();

        assertTrue(result.isEmpty());
    }
}