package br.com.fiap.postech.products.infrastructure.batch.config;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@RequiredArgsConstructor
public class JobConfig {

    private final Step importStep;
    private final Step moveFilesStep;
    private final JobRepository jobRepository;

    @Bean
    public Job importarProdutosJob() {
        return new JobBuilder("import-products", jobRepository)
                .start(importStep)
                .next(moveFilesStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }
}