package br.com.fiap.postech.products.application.usecase;

import br.com.fiap.postech.products.model.UpdateProductStockRequest;
import jakarta.validation.Valid;

import java.util.List;

public interface UpdateProductStockUseCase {

    void execute(List<@Valid UpdateProductStockRequest> updateProductStockRequest);
}
