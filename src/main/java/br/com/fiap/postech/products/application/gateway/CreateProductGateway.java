package br.com.fiap.postech.products.application.gateway;

import br.com.fiap.postech.products.model.ProductApiModel;

public interface CreateProductGateway {
    ProductApiModel createProduct(ProductApiModel product);
}
