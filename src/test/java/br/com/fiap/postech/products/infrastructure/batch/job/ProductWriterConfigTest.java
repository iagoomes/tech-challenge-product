package br.com.fiap.postech.products.infrastructure.batch.job;

import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JdbcBatchItemWriter;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertNotNull;

class ProductWriterConfigTest {

    private ProductWriterConfig productWriterConfig;

    @Mock
    private DataSource dataSource;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        productWriterConfig = new ProductWriterConfig(dataSource);
    }

    @Test
    void writer_shouldReturnNonNullItemWriter() {
        ItemWriter<ProductEntity> writer = productWriterConfig.writer();
        assertNotNull(writer);
    }

    @Test
    void writer_shouldConfigureJdbcBatchItemWriterCorrectly() {
        ItemWriter<ProductEntity> writer = productWriterConfig.writer();
        assertNotNull(writer);
        assert (writer instanceof JdbcBatchItemWriter);

        JdbcBatchItemWriter<ProductEntity> jdbcWriter = (JdbcBatchItemWriter<ProductEntity>) writer;
        assertNotNull(jdbcWriter);
    }
}