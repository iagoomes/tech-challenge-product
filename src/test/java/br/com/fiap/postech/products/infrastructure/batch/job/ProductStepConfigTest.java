package br.com.fiap.postech.products.infrastructure.batch.job;

import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.transaction.PlatformTransactionManager;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

class ProductStepConfigTest {

    @Mock
    private JobRepository jobRepository;

    @Mock
    private PlatformTransactionManager transactionManager;

    @Mock
    private ItemReader<ProductEntity> reader;

    @Mock
    private ItemWriter<ProductEntity> writer;

    @Mock
    private ProductTaskletConfig productTaskletConfig;

    private ProductStepConfig productStepConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productStepConfig = new ProductStepConfig(jobRepository, transactionManager, reader, writer, productTaskletConfig);
    }

    @Test
    void importStep_createsStepSuccessfully() {
        Step step = productStepConfig.importStep();
        assertNotNull(step);
    }

    @Test
    void moveFilesStep_createsStepSuccessfully() {
        when(productTaskletConfig.moveFilesTasklet()).thenReturn(null);
        Step step = productStepConfig.moveFilesStep();
        assertNotNull(step);
    }
}