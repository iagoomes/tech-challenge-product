package br.com.fiap.postech.products.config;

import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.io.File;

@Configuration
@RequiredArgsConstructor
@Slf4j
public class BatchConfig {
    private final PlatformTransactionManager transactionManager;
    @Value("${upload.directory}")
    private String directory;

    @Bean
    public Job job(@Qualifier("passoInicial") Step passoInicial,
                   @Qualifier("moverArquivosStep") Step moverArquivosStep,
                   JobRepository jobRepository) {
        return new JobBuilder("importar-arquivos", jobRepository)
                .start(passoInicial)
                .next(moverArquivosStep)
                .incrementer(new RunIdIncrementer())
                .build();
    }

    @Bean
    public Step passoInicial(
            @Qualifier("reader") ItemReader<ProductEntity> reader,
            @Qualifier("writer") ItemWriter<ProductEntity> writer,
            JobRepository jobRepository) {
        return new StepBuilder("passo-inicial", jobRepository)
                .<ProductEntity, ProductEntity>chunk(200, transactionManager)
                .reader(reader)
                .writer(writer)
                .allowStartIfComplete(true)
                .build();
    }

    @Bean
    public ItemReader<ProductEntity> reader() {
        return new FlatFileItemReaderBuilder<ProductEntity>()
                .name("csv-reader")
                .resource(new FileSystemResource(directory + "/products.csv"))
                .linesToSkip(1)
//                .comments("--")
                .delimited()
                .delimiter(",")
                .names("name", "description", "price", "stockQuantity")
                .fieldSetMapper(new ProductEntityMapper())
                .build();
    }

    @Bean
    public ItemWriter<ProductEntity> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<ProductEntity>()
                .dataSource(dataSource)
                .sql("INSERT INTO TB_PRODUCT (NAME, DESCRIPTION, PRICE, STOCKQUANTITY) VALUES (:name, :description, :price, :stockQuantity)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }

    @Bean
    public Tasklet moverArquivosTasklet() {
        return (contribution, chunkContext) -> {
            File pastaOrigem = new File(directory);
            File pastaDestino = new File(directory + "/processados");

            if (!pastaDestino.exists()) {
                pastaDestino.mkdirs();
            }

            File[] arquivos = pastaOrigem.listFiles((dir, name) -> name.endsWith(".csv"));

            if (arquivos != null) {
                for (File arquivo : arquivos) {
                    File arquivoDestino = new File(pastaDestino, arquivo.getName());
                    if (arquivo.renameTo(arquivoDestino)) {
                        log.info("Arquivo movido: {}", arquivo.getName());
                    } else {
                        log.warn("Não foi possível mover o arquivo: {}", arquivo.getName());
                        throw new RuntimeException("Não foi possível mover o arquivo: " + arquivo.getName());//NOSONAR
                    }
                }
            }
            return RepeatStatus.FINISHED;
        };
    }

    @Bean
    public Step moverArquivosStep(JobRepository jobRepository) {
        return new StepBuilder("move-file", jobRepository)
                .tasklet(moverArquivosTasklet(), transactionManager)
                .allowStartIfComplete(true)
                .build();
    }


}
