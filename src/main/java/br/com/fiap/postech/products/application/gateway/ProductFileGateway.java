package br.com.fiap.postech.products.application.gateway;

import br.com.fiap.postech.products.domain.entity.LoadProduct;

public interface ProductFileGateway {
    void saveFile(LoadProduct loadProduct);
}
