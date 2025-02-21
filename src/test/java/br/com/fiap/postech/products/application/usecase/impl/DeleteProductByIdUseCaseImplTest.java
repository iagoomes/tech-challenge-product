package br.com.fiap.postech.products.application.usecase.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.fiap.postech.products.application.gateway.DeleteProductByIdGateway;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DeleteProductByIdUseCaseImplTest {

    private DeleteProductByIdUseCaseImpl deleteProductByIdUseCase;
    private DeleteProductByIdGateway deleteProductByIdGateway;

    @BeforeEach
    void setUp() {
        deleteProductByIdGateway = mock(DeleteProductByIdGateway.class);
        deleteProductByIdUseCase = new DeleteProductByIdUseCaseImpl(deleteProductByIdGateway);
    }

    @Test
    void execute_ValidId_DeletesProduct() {
        Long productId = 1L;

        deleteProductByIdUseCase.execute(productId);

        verify(deleteProductByIdGateway, times(1)).deleteProductById(productId);
    }


    @Test
    void execute_NonExistentId_DoesNotThrowException() {
        Long productId = 999L;
        doNothing().when(deleteProductByIdGateway).deleteProductById(productId);

        assertDoesNotThrow(() -> deleteProductByIdUseCase.execute(productId));
    }
}