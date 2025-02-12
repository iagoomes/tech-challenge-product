package br.com.fiap.postech.products.application.gateway.impl;

import br.com.fiap.postech.products.application.gateway.CreateProductGateway;
import br.com.fiap.postech.products.infrastructure.gateway.ProductRepositoryGateway;
import br.com.fiap.postech.products.infrastructure.mapper.ProductMapper;
import br.com.fiap.postech.products.model.ProductApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProductGatewayImpl implements CreateProductGateway {

    private final ProductRepositoryGateway productRepositoryGateway;
    private final ProductMapper productMapper;

    @Override
    public ProductApiModel createProduct(ProductApiModel dto) {
        return productMapper.modelToDTO(productRepositoryGateway.save(productMapper.DTOToModel(dto)));
    }

}
