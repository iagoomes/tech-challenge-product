package br.com.fiap.postech.products.infrastructure.batch.config;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilderException;
import org.springframework.batch.core.repository.JobRepository;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

class JobConfigTest {

    @Mock
    private Step importStep;

    @Mock
    private Step moveFilesStep;

    @Mock
    private JobRepository jobRepository;

    private JobConfig jobConfig;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        jobConfig = new JobConfig(importStep, moveFilesStep, jobRepository);
    }

    @Test
    void importarProdutosJob_createsJobSuccessfully() {
        Job job = jobConfig.importarProdutosJob();
        assertNotNull(job);
    }

    @Test
    void importarProdutosJob_throwsExceptionWhenJobRepositoryIsNull() {
        jobConfig = new JobConfig(importStep, moveFilesStep, null);
        assertThrows(JobBuilderException.class, () -> jobConfig.importarProdutosJob());
    }

}