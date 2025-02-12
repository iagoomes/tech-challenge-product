package br.com.fiap.postech.products.application.usecase;

import br.com.fiap.postech.products.model.ProductApiModel;

import java.util.List;

public interface GetAllProductsUseCase {
    List<ProductApiModel> execute();
}
