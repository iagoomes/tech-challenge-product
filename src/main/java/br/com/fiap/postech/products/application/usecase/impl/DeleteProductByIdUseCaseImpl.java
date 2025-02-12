package br.com.fiap.postech.products.application.usecase.impl;

import br.com.fiap.postech.products.application.gateway.DeleteProductByIdGateway;
import br.com.fiap.postech.products.application.usecase.DeleteProductByIdUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteProductByIdUseCaseImpl implements DeleteProductByIdUseCase {
    private final DeleteProductByIdGateway deleteProductByIdGateway;

    @Override
    public void execute(Long id) {
        deleteProductByIdGateway.deleteProductById(id);
    }
}
