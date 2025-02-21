package br.com.fiap.postech.products.infrastructure.batch.job;

import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

@Configuration
@RequiredArgsConstructor
public class ProductStepConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;
    private final ItemReader<ProductEntity> reader;
    private final ItemWriter<ProductEntity> writer;
    private final ProductTaskletConfig productTaskletConfig;

    @Bean
    public Step importStep() {
        return new StepBuilder("import-step", jobRepository)
                .<ProductEntity, ProductEntity>chunk(200, transactionManager)
                .reader(reader)
                .writer(writer)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public Step moveFilesStep() {
        return new StepBuilder("move-files-step", jobRepository)
                .tasklet(productTaskletConfig.moveFilesTasklet(), transactionManager)
                .allowStartIfComplete(true)
                .build();
    }
}
