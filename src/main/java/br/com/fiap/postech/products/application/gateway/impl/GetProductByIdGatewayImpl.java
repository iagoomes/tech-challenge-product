package br.com.fiap.postech.products.application.gateway.impl;

import br.com.fiap.postech.products.application.gateway.GetProductByIdGateway;
import br.com.fiap.postech.products.infrastructure.gateway.ProductRepositoryGateway;
import br.com.fiap.postech.products.infrastructure.mapper.ProductMapper;
import br.com.fiap.postech.products.model.ProductApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductByIdGatewayImpl implements GetProductByIdGateway {
    private final ProductRepositoryGateway productRepositoryGateway;
    private final ProductMapper productMapper;

    @Override
    public ProductApiModel getProductById(Long id) {
        return productMapper.modelToDTO(productRepositoryGateway.findById(id));
    }
}
