package br.com.fiap.postech.products.application.gateway.impl;

import br.com.fiap.postech.products.application.gateway.GetAllProductsGateway;
import br.com.fiap.postech.products.infrastructure.gateway.ProductRepositoryGateway;
import br.com.fiap.postech.products.infrastructure.mapper.ProductMapper;
import br.com.fiap.postech.products.model.ProductApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllProductsGatewayImpl implements GetAllProductsGateway {
    private final ProductRepositoryGateway productRepositoryGateway;
    private final ProductMapper productMapper;


    @Override
    public List<ProductApiModel> getAllProducts() {
        return productRepositoryGateway.findAll().stream().map(productMapper::modelToDTO).toList();
    }
}
