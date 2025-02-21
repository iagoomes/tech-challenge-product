package br.com.fiap.postech.products.application.usecase.impl;

import br.com.fiap.postech.products.application.gateway.ProductBatchGateway;
import br.com.fiap.postech.products.application.gateway.ProductFileGateway;
import br.com.fiap.postech.products.domain.entity.LoadProduct;
import br.com.fiap.postech.products.model.ProductCsvUploadResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductBatchUploaderUseCaseImplTest {

    private ProductBatchUploaderUseCaseImpl productBatchUploaderUseCase;

    @Mock
    private ProductFileGateway productFileGateway;

    @Mock
    private ProductBatchGateway productBatchGateway;

    @Mock
    private LoadProduct loadProduct;

    @Mock
    private ProductCsvUploadResponse productCsvUploadResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productBatchUploaderUseCase = new ProductBatchUploaderUseCaseImpl(productFileGateway, productBatchGateway);
    }

    @Test
    void execute_shouldSaveFileAndStartBatchJobSuccessfully() {
        when(productBatchGateway.startBatchJob(loadProduct)).thenReturn(productCsvUploadResponse);

        ProductCsvUploadResponse response = productBatchUploaderUseCase.execute(loadProduct);

        verify(productFileGateway).saveFile(loadProduct);
        verify(productBatchGateway).startBatchJob(loadProduct);
        assertEquals(productCsvUploadResponse, response);
    }

    @Test
    void execute_shouldThrowException_whenSaveFileFails() {
        doThrow(new RuntimeException("File save failed")).when(productFileGateway).saveFile(loadProduct);

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productBatchUploaderUseCase.execute(loadProduct);
        });

        assertEquals("File save failed", exception.getMessage());
        verify(productFileGateway).saveFile(loadProduct);
        verify(productBatchGateway, never()).startBatchJob(loadProduct);
    }

    @Test
    void execute_shouldThrowException_whenStartBatchJobFails() {
        when(productBatchGateway.startBatchJob(loadProduct)).thenThrow(new RuntimeException("Batch job failed"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            productBatchUploaderUseCase.execute(loadProduct);
        });

        assertEquals("Batch job failed", exception.getMessage());
        verify(productFileGateway).saveFile(loadProduct);
        verify(productBatchGateway).startBatchJob(loadProduct);
    }
}