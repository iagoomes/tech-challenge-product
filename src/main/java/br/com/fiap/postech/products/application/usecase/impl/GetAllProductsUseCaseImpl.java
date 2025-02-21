package br.com.fiap.postech.products.application.usecase.impl;

import br.com.fiap.postech.products.application.gateway.GetAllProductsGateway;
import br.com.fiap.postech.products.application.usecase.GetAllProductsUseCase;
import br.com.fiap.postech.products.model.ProductApiModel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetAllProductsUseCaseImpl implements GetAllProductsUseCase {
    private final GetAllProductsGateway getAllProductsGateway;

    @Override
    public List<ProductApiModel> execute() {
        return getAllProductsGateway.getAllProducts();
    }
}
