package br.com.fiap.postech.products.application.usecase.impl;

import br.com.fiap.postech.products.application.gateway.GetProductByIdGateway;
import br.com.fiap.postech.products.application.usecase.GetProductByIdUseCase;
import br.com.fiap.postech.products.model.ProductApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class GetProductByIdUseCaseImpl implements GetProductByIdUseCase {
    private final GetProductByIdGateway getProductByIdGateway;

    public ProductApiModel execute(Long id) {
        return getProductByIdGateway.getProductById(id);
    }
}
