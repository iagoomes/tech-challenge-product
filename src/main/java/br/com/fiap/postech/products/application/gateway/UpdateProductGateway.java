package br.com.fiap.postech.products.application.gateway;

import br.com.fiap.postech.products.model.ProductApiModel;

public interface UpdateProductGateway {
    ProductApiModel updateProduct(Long id, ProductApiModel dto);
}
