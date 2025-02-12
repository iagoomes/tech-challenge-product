package br.com.fiap.postech.products.application.gateway;

import br.com.fiap.postech.products.model.ProductApiModel;

import java.util.List;

public interface GetAllProductsGateway {
    List<ProductApiModel> getAllProducts();
}
