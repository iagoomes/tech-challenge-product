package br.com.fiap.postech.products.infrastructure.batch.job;

import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;

@Configuration
@RequiredArgsConstructor
public class ProductReaderConfig {

    @Value("${upload.directory}")
    private String directory;

    @Bean
    public ItemReader<ProductEntity> reader() {
        return new FlatFileItemReaderBuilder<ProductEntity>()
                .name("csv-reader")
                .resource(new FileSystemResource(directory + "/products.csv"))
                .linesToSkip(1)
                .delimited()
                .delimiter(",")
                .names("name", "description", "price", "stockQuantity")
                .fieldSetMapper(new ProductEntityMapper())
                .build();
    }
}
