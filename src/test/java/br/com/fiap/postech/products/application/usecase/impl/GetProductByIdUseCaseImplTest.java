package br.com.fiap.postech.products.application.usecase.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.fiap.postech.products.application.gateway.GetProductByIdGateway;
import br.com.fiap.postech.products.model.ProductApiModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class GetProductByIdUseCaseImplTest {

    private GetProductByIdUseCaseImpl getProductByIdUseCase;
    private GetProductByIdGateway getProductByIdGateway;

    @BeforeEach
    void setUp() {
        getProductByIdGateway = mock(GetProductByIdGateway.class);
        getProductByIdUseCase = new GetProductByIdUseCaseImpl(getProductByIdGateway);
    }

    @Test
    void execute_ValidId_ReturnsProduct() {
        Long productId = 1L;
        ProductApiModel productApiModel = new ProductApiModel();
        when(getProductByIdGateway.getProductById(productId)).thenReturn(productApiModel);

        ProductApiModel result = getProductByIdUseCase.execute(productId);

        assertEquals(productApiModel, result);
    }

    @Test
    void execute_NonExistentId_ReturnsNull() {
        Long productId = 999L;
        when(getProductByIdGateway.getProductById(productId)).thenReturn(null);

        ProductApiModel result = getProductByIdUseCase.execute(productId);

        assertNull(result);
    }

    @Test
    void execute_GatewayThrowsException_ThrowsException() {
        Long productId = 1L;
        when(getProductByIdGateway.getProductById(productId)).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> getProductByIdUseCase.execute(productId));
    }
}