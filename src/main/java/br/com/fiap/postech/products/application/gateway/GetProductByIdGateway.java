package br.com.fiap.postech.products.application.gateway;

import br.com.fiap.postech.products.model.ProductApiModel;

public interface GetProductByIdGateway {
    ProductApiModel getProductById(Long id);
}
