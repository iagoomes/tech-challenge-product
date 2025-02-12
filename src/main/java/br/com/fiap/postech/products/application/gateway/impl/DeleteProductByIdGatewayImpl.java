package br.com.fiap.postech.products.application.gateway.impl;

import br.com.fiap.postech.products.application.gateway.DeleteProductByIdGateway;
import br.com.fiap.postech.products.infrastructure.gateway.ProductRepositoryGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DeleteProductByIdGatewayImpl implements DeleteProductByIdGateway {
    private final ProductRepositoryGateway productRepositoryGateway;

    @Override
    public void deleteProductById(Long id) {
        if (productRepositoryGateway.existsById(id)) {
            productRepositoryGateway.deleteById(id);
        }
    }
}
