package br.com.fiap.postech.products.application.gateway;

import br.com.fiap.postech.products.domain.entity.LoadProduct;
import br.com.fiap.postech.products.model.ProductCsvUploadResponse;

public interface ProductBatchGateway {
    ProductCsvUploadResponse startBatchJob(LoadProduct loadProduct);
}
