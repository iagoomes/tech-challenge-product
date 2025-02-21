package br.com.fiap.postech.products.application.usecase;

import br.com.fiap.postech.products.model.ProductApiModel;

public interface UpdateProductUseCase {
    ProductApiModel execute(Long id, ProductApiModel dto);
}
