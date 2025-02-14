package br.com.fiap.postech.products.application.usecase.impl;

import br.com.fiap.postech.products.application.gateway.ProductBatchGateway;
import br.com.fiap.postech.products.application.gateway.ProductFileGateway;
import br.com.fiap.postech.products.application.usecase.ProductBatchUploaderUseCase;
import br.com.fiap.postech.products.domain.entity.LoadProduct;
import br.com.fiap.postech.products.model.ProductCsvUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductBatchUploaderUseCaseImpl implements ProductBatchUploaderUseCase {

    private final ProductFileGateway productFileGateway;
    private final ProductBatchGateway productBatchGateway;

    @Override
    public ProductCsvUploadResponse execute(LoadProduct loadProduct) {
        productFileGateway.saveFile(loadProduct);
        return productBatchGateway.startBatchJob(loadProduct);
    }
}

