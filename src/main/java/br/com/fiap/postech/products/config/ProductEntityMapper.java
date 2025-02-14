package br.com.fiap.postech.products.config;

import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import io.micrometer.common.lang.NonNull;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;

import java.math.BigDecimal;

public class ProductEntityMapper implements FieldSetMapper<ProductEntity> {
    @Override
    public @NonNull ProductEntity mapFieldSet(@NonNull FieldSet fieldSet) {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(fieldSet.readString("name"));
        productEntity.setDescription(fieldSet.readString("description"));
        productEntity.setPrice(new BigDecimal(fieldSet.readString("price")));
        productEntity.setStockQuantity(fieldSet.readInt("stockQuantity"));
        return productEntity;
    }
}