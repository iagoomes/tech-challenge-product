package br.com.fiap.postech.products.application.gateway;

import br.com.fiap.postech.products.model.UpdateProductStockRequest;

import java.util.List;

public interface UpdateProductStockGateway {
    void updateProductStock(List<UpdateProductStockRequest> updateProductStockRequest);
}
