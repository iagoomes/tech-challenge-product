package br.com.fiap.postech.products.application.gateway.impl;

import br.com.fiap.postech.products.domain.entity.Product;
import br.com.fiap.postech.products.infrastructure.gateway.ProductRepositoryGateway;
import br.com.fiap.postech.products.model.UpdateProductStockRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateProductStockGatewayImplTest {

    @Mock
    private ProductRepositoryGateway productRepositoryGateway;

    @InjectMocks
    private UpdateProductStockGatewayImpl updateProductStockGatewayImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void updateProductStock_UpdatesStockSuccessfully() {
        List<UpdateProductStockRequest> request = List.of(new UpdateProductStockRequest(1L, 10));
        Product product = mock(Product.class);
        when(productRepositoryGateway.findById(1L)).thenReturn(product);

        updateProductStockGatewayImpl.updateProductStock(request);

        verify(product).substractStock(10);
        verify(productRepositoryGateway).save(product);
    }

    @Test
    void updateProductStock_ThrowsExceptionWhenProductNotFound() {
        List<UpdateProductStockRequest> request = List.of(new UpdateProductStockRequest(999L, 10));
        when(productRepositoryGateway.findById(999L)).thenReturn(null);

        assertThrows(RuntimeException.class, () -> updateProductStockGatewayImpl.updateProductStock(request));
    }

    @Test
    void updateProductStock_ThrowsExceptionWhenInsufficientStock() {
        List<UpdateProductStockRequest> request = List.of(new UpdateProductStockRequest(1L, 1000));
        Product product = mock(Product.class);
        when(productRepositoryGateway.findById(1L)).thenReturn(product);
        doThrow(new IllegalArgumentException("Insufficient stock quantity")).when(product).substractStock(1000);

        assertThrows(IllegalArgumentException.class, () -> updateProductStockGatewayImpl.updateProductStock(request));
    }
}