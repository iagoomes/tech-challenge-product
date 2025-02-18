package br.com.fiap.postech.products.application.gateway.impl;

import br.com.fiap.postech.products.domain.entity.LoadProduct;
import br.com.fiap.postech.products.exception.JobProcessingException;
import br.com.fiap.postech.products.model.ProductCsvUploadResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ProductBatchGatewayImplTest {

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job job;

    @InjectMocks
    private ProductBatchGatewayImpl productBatchGateway;

    private LoadProduct loadProduct;

    @BeforeEach
    void setup() {
        loadProduct = new LoadProduct(new byte[]{1, 2, 3}); // Sample binary data
    }

    @Test
    void testStartBatchJob_Success() throws Exception {
        // Call the method under test
        ProductCsvUploadResponse response = productBatchGateway.startBatchJob(loadProduct);

        // Verify interactions
        verify(jobLauncher).run(any(Job.class), any(JobParameters.class));

        // Assert the response
        assertEquals("Job finished", response.getMessage());
    }

    @Test
    void testStartBatchJob_Exception() throws Exception {
        // Simulate an exception thrown by the jobLauncher
        doThrow(new RuntimeException("Failed to run job")).when(jobLauncher).run(any(Job.class), any(JobParameters.class));

        // Verify that a JobProcessingException is thrown
        JobProcessingException exception = assertThrows(JobProcessingException.class, () -> productBatchGateway.startBatchJob(loadProduct));

        // Check the exception message
        assertEquals("Error processing job", exception.getMessage());
    }
}