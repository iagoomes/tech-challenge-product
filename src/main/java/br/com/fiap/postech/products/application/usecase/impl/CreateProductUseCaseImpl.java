package br.com.fiap.postech.products.application.usecase.impl;

import br.com.fiap.postech.products.application.gateway.CreateProductGateway;
import br.com.fiap.postech.products.application.usecase.CreateProductUseCase;
import br.com.fiap.postech.products.model.ProductApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CreateProductUseCaseImpl implements CreateProductUseCase {

    private final CreateProductGateway createProductGateway;

    @Override
    public ProductApiModel execute(ProductApiModel dto) {
        return createProductGateway.createProduct(dto);
    }
}
