package br.com.fiap.postech.products.application.usecase.impl;

import br.com.fiap.postech.products.application.gateway.UpdateProductStockGateway;
import br.com.fiap.postech.products.model.UpdateProductStockRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class UpdateProductStockUseCaseImplTest {

    @Mock
    private UpdateProductStockGateway updateProductStockGateway;

    @InjectMocks
    private UpdateProductStockUseCaseImpl updateProductStockUseCaseImpl;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void execute_UpdatesStockSuccessfully() {
        List<UpdateProductStockRequest> request = List.of(new UpdateProductStockRequest(1L, 10));
        doNothing().when(updateProductStockGateway).updateProductStock(request);

        updateProductStockUseCaseImpl.execute(request);

        verify(updateProductStockGateway).updateProductStock(request);
    }

    @Test
    void execute_ThrowsExceptionWhenProductNotFound() {
        List<UpdateProductStockRequest> request = List.of(new UpdateProductStockRequest(999L, 10));
        doThrow(new RuntimeException("Product not found")).when(updateProductStockGateway).updateProductStock(request);

        assertThrows(RuntimeException.class, () -> updateProductStockUseCaseImpl.execute(request));
    }

    @Test
    void execute_ThrowsExceptionWhenInsufficientStock() {
        List<UpdateProductStockRequest> request = List.of(new UpdateProductStockRequest(1L, 1000));
        doThrow(new IllegalArgumentException("Insufficient stock quantity")).when(updateProductStockGateway).updateProductStock(request);

        assertThrows(IllegalArgumentException.class, () -> updateProductStockUseCaseImpl.execute(request));
    }
}