package br.com.fiap.postech.products.application.usecase.impl;

import br.com.fiap.postech.products.application.gateway.UpdateProductGateway;
import br.com.fiap.postech.products.application.usecase.UpdateProductUseCase;
import br.com.fiap.postech.products.model.ProductApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateProductUseCaseImpl implements UpdateProductUseCase {
    private final UpdateProductGateway updateProductGateway;

    @Override
    public ProductApiModel execute(Long id, ProductApiModel dto) {
        return updateProductGateway.updateProduct(id, dto);
    }
}
