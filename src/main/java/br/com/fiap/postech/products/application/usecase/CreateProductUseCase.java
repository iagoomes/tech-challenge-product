package br.com.fiap.postech.products.application.usecase;

import br.com.fiap.postech.products.model.ProductApiModel;

public interface CreateProductUseCase {
    ProductApiModel execute(ProductApiModel dto);
}
