package br.com.fiap.postech.products.application.gateway.impl;

import static org.mockito.Mockito.*;

import br.com.fiap.postech.products.infrastructure.gateway.ProductRepositoryGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeleteProductByIdGatewayImplTest {

    private DeleteProductByIdGatewayImpl deleteProductByIdGateway;
    private ProductRepositoryGateway productRepositoryGateway;

    @BeforeEach
    void setUp() {
        productRepositoryGateway = mock(ProductRepositoryGateway.class);
        deleteProductByIdGateway = new DeleteProductByIdGatewayImpl(productRepositoryGateway);
    }

    @Test
    void deleteProductById_ProductExists_DeletesProduct() {
        Long productId = 1L;
        when(productRepositoryGateway.existsById(productId)).thenReturn(true);

        deleteProductByIdGateway.deleteProductById(productId);

        verify(productRepositoryGateway, times(1)).deleteById(productId);
    }

    @Test
    void deleteProductById_ProductDoesNotExist_DoesNotDeleteProduct() {
        Long productId = 1L;
        when(productRepositoryGateway.existsById(productId)).thenReturn(false);

        deleteProductByIdGateway.deleteProductById(productId);

        verify(productRepositoryGateway, never()).deleteById(productId);
    }
}