package br.com.fiap.postech.products.application.gateway.impl;

import br.com.fiap.postech.products.application.gateway.UpdateProductStockGateway;
import br.com.fiap.postech.products.domain.entity.Product;
import br.com.fiap.postech.products.infrastructure.gateway.ProductRepositoryGateway;
import br.com.fiap.postech.products.model.UpdateProductStockRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UpdateProductStockGatewayImpl implements UpdateProductStockGateway {
    private final ProductRepositoryGateway productRepositoryGateway;

    @Override
    public void updateProductStock(List<UpdateProductStockRequest> updateProductStockRequest) {
        updateProductStockRequest.forEach(up ->  {
            Product product = productRepositoryGateway.findById(up.getProductId());
            product.substractStock(up.getQuantity());
            productRepositoryGateway.save(product);
        });
    }
}
