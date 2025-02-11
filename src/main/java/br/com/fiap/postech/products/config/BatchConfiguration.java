package br.com.fiap.postech.products.config;

import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@EnableBatchProcessing
public class BatchConfiguration {

    @Autowired
    private JobLauncher jobLauncher;

    @Bean
    public Job processProductCsv(JobRepository jobRepository, Step step) {
        return new JobBuilder("processProductCsv", jobRepository)
                .start(step)
                .build();
    }

    // Método para executar o job com o caminho do arquivo como argumento
    public void runJob(Job job, String filePath) {
        try {
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("filePath", filePath)
                    .toJobParameters();
            jobLauncher.run(job, jobParameters);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Bean
    public Step step(JobRepository jobRepository,
                     PlatformTransactionManager platformTransactionManager,
                     ItemReader<ProductEntity> reader,
                     ItemWriter<ProductEntity> writer) {
        return new StepBuilder("step", jobRepository)
                .<ProductEntity, ProductEntity>chunk(20, platformTransactionManager)
                .reader(reader)
                .writer(writer)
                .build();
    }

    @Bean
    @StepScope
    public ItemReader<ProductEntity> reader(@Value("#{jobParameters['filePath']}") String filePath) {
        BeanWrapperFieldSetMapper<ProductEntity> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
        fieldSetMapper.setTargetType(ProductEntity.class);

        return new FlatFileItemReaderBuilder<ProductEntity>()
                .name("productItemReader")
                .resource(new FileSystemResource(filePath))
                .delimited()
                .names("name", "description", "price", "stockQuantity")
                .linesToSkip(1)
                .fieldSetMapper(fieldSetMapper)
                .build();
    }

    @Bean
    public ItemWriter<ProductEntity> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ProductEntity>()
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .dataSource(dataSource)
                .sql("INSERT INTO TB_PRODUCT (name, description, price, stock_quantity) VALUES (:name, :description, :price, :stockQuantity)")
                .build();
    }
}