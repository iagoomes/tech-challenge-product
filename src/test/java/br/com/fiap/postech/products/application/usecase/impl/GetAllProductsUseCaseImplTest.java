package br.com.fiap.postech.products.application.usecase.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import br.com.fiap.postech.products.application.gateway.GetAllProductsGateway;
import br.com.fiap.postech.products.model.ProductApiModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

class GetAllProductsUseCaseImplTest {

    private GetAllProductsUseCaseImpl getAllProductsUseCase;
    private GetAllProductsGateway getAllProductsGateway;

    @BeforeEach
    void setUp() {
        getAllProductsGateway = mock(GetAllProductsGateway.class);
        getAllProductsUseCase = new GetAllProductsUseCaseImpl(getAllProductsGateway);
    }

    @Test
    void execute_ProductsExist_ReturnsListOfProducts() {
        List<ProductApiModel> products = List.of(new ProductApiModel(), new ProductApiModel());
        when(getAllProductsGateway.getAllProducts()).thenReturn(products);

        List<ProductApiModel> result = getAllProductsUseCase.execute();

        assertEquals(products, result);
    }

    @Test
    void execute_NoProductsExist_ReturnsEmptyList() {
        when(getAllProductsGateway.getAllProducts()).thenReturn(Collections.emptyList());

        List<ProductApiModel> result = getAllProductsUseCase.execute();

        assertTrue(result.isEmpty());
    }

    @Test
    void execute_GatewayThrowsException_ThrowsException() {
        when(getAllProductsGateway.getAllProducts()).thenThrow(new RuntimeException("Database error"));

        assertThrows(RuntimeException.class, () -> getAllProductsUseCase.execute());
    }
}