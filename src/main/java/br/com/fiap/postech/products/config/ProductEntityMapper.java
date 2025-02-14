package br.com.fiap.postech.products.config;

import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FieldSet;
import org.springframework.lang.NonNullApi;
import org.springframework.validation.BindException;

import java.math.BigDecimal;

public class ProductEntityMapper implements FieldSetMapper<ProductEntity> {
    @NonNullApi
    public ProductEntity mapFieldSet(FieldSet fieldSet) throws BindException {
        ProductEntity productEntity = new ProductEntity();
        productEntity.setName(fieldSet.readString("name"));
        productEntity.setDescription(fieldSet.readString("description"));
        productEntity.setPrice(new BigDecimal(fieldSet.readString("price")));
        productEntity.setStockQuantity(fieldSet.readInt("stockQuantity"));
        return productEntity;
    }
}
