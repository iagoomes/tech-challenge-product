package br.com.fiap.postech.products.infrastructure.batch.job;


import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.FileSystemResource;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.io.FileWriter;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = ProductReaderConfig.class)
@TestPropertySource(properties = "upload.directory=src/test/resources")
public class ProductReaderConfigTest {

    @Autowired
    private ProductReaderConfig productReaderConfig;

    @BeforeEach
    public void setup() throws Exception {
        // Criar um arquivo CSV de exemplo para testes
        String data = "name,description,price,stockQuantity\n"   // Cabeçalhos
                + "Product1,Description1,25.50,10\n"        // Linha 1
                + "Product2,Description2,13.40,5\n";        // Linha 2

        FileSystemResource resource = new FileSystemResource("src/test/resources/products.csv");
        FileWriter writer = new FileWriter(resource.getFile());
        writer.write(data);
        writer.close();
    }

    @Test
    public void testReader() throws Exception {
        ItemReader<ProductEntity> reader = productReaderConfig.reader();

        // Criar contexto de execução para o reader
        ExecutionContext executionContext = new ExecutionContext();
        ((FlatFileItemReader<ProductEntity>) reader).open(executionContext);

        try {
            ProductEntity product1 = reader.read();
            assertThat(product1).isNotNull();
            assertThat(product1.getName()).isEqualTo("Product1");
            assertThat(product1.getDescription()).isEqualTo("Description1");
            assertThat(product1.getPrice()).isEqualByComparingTo("25.50");
            assertThat(product1.getStockQuantity()).isEqualTo(10);

            ProductEntity product2 = reader.read();
            assertThat(product2).isNotNull();
            assertThat(product2.getName()).isEqualTo("Product2");
            assertThat(product2.getDescription()).isEqualTo("Description2");
            assertThat(product2.getPrice()).isEqualByComparingTo("13.40");
            assertThat(product2.getStockQuantity()).isEqualTo(5);

            ProductEntity product3 = reader.read();
            assertThat(product3).isNull(); // Espera que não haja mais produtos
        } finally {
            ((FlatFileItemReader<ProductEntity>) reader).close();
        }
    }

}