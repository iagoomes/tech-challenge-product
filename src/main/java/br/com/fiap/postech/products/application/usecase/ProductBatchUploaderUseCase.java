package br.com.fiap.postech.products.application.usecase;

import br.com.fiap.postech.products.domain.entity.LoadProduct;
import br.com.fiap.postech.products.model.ProductCsvUploadResponse;

public interface ProductBatchUploaderUseCase {
    ProductCsvUploadResponse execute(LoadProduct file);
}
