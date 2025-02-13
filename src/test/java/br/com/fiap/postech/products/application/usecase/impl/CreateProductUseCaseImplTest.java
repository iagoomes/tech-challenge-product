package br.com.fiap.postech.products.application.usecase.impl;

import br.com.fiap.postech.products.application.gateway.CreateProductGateway;
import br.com.fiap.postech.products.exception.InvalidProductException;
import br.com.fiap.postech.products.model.ProductApiModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


class CreateProductUseCaseImplTest {

    private CreateProductUseCaseImpl createProductUseCase;
    private CreateProductGateway createProductGateway;

    @BeforeEach
    void setUp() {
        createProductGateway = mock(CreateProductGateway.class);
        createProductUseCase = new CreateProductUseCaseImpl(createProductGateway);
    }

    @Test
    void execute_ValidProduct_ReturnsCreatedProduct() {
        ProductApiModel productApiModel = new ProductApiModel();
        when(createProductGateway.createProduct(productApiModel)).thenReturn(productApiModel);

        ProductApiModel result = createProductUseCase.execute(productApiModel);

        assertEquals(productApiModel, result);
    }

    @Test
    void execute_NullProduct_ThrowsInvalidProductException() {
        assertThrows(InvalidProductException.class, () -> createProductUseCase.execute(null));
    }

    @Test
    void execute_ProductWithNullName_ThrowsInvalidProductException() {
        assertThrows(InvalidProductException.class, () -> createProductUseCase.execute(null));
    }

}