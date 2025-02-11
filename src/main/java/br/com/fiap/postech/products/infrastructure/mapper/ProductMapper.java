package br.com.fiap.postech.products.infrastructure.mapper;

import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;

import br.com.fiap.postech.products.domain.entity.Product;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    ProductEntity toEntity(Product product);
    Product toModel(ProductEntity productEntity);
}
