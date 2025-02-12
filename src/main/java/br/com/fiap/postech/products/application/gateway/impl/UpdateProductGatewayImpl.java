package br.com.fiap.postech.products.application.gateway.impl;

import br.com.fiap.postech.products.application.gateway.UpdateProductGateway;
import br.com.fiap.postech.products.infrastructure.gateway.ProductRepositoryGateway;
import br.com.fiap.postech.products.infrastructure.mapper.ProductMapper;
import br.com.fiap.postech.products.model.ProductApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateProductGatewayImpl implements UpdateProductGateway {
    private final ProductRepositoryGateway productRepository;
    private final ProductMapper productMapper;

    @Override
    public ProductApiModel updateProduct(Long id, ProductApiModel dto) {
        return null;
    }
}
