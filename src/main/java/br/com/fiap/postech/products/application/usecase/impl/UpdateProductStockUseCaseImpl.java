package br.com.fiap.postech.products.application.usecase.impl;

import br.com.fiap.postech.products.application.gateway.UpdateProductStockGateway;
import br.com.fiap.postech.products.application.usecase.UpdateProductStockUseCase;
import br.com.fiap.postech.products.model.UpdateProductStockRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Component
public class UpdateProductStockUseCaseImpl implements UpdateProductStockUseCase {

    private final UpdateProductStockGateway updateProductStockGateway;

    @Override
    public void execute(List<@Valid UpdateProductStockRequest> updateProductStockRequest) {
        updateProductStockGateway.updateProductStock(updateProductStockRequest);
    }
}
