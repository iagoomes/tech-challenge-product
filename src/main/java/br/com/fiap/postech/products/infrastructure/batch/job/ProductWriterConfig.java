package br.com.fiap.postech.products.infrastructure.batch.job;

import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.BeanPropertyItemSqlParameterSourceProvider;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class ProductWriterConfig {

    private final DataSource dataSource;

    @Bean
    public ItemWriter<ProductEntity> writer() {
        return new JdbcBatchItemWriterBuilder<ProductEntity>()
                .dataSource(dataSource)
                .sql("INSERT INTO TB_PRODUCT (NAME, DESCRIPTION, PRICE, STOCKQUANTITY) VALUES (:name, :description, :price, :stockQuantity)")
                .itemSqlParameterSourceProvider(new BeanPropertyItemSqlParameterSourceProvider<>())
                .build();
    }
}