package br.com.fiap.postech.products.application.usecase.impl;

import br.com.fiap.postech.products.application.gateway.UploadArchiveGateway;
import br.com.fiap.postech.products.model.ProductCsvUploadResponse;
import br.com.fiap.postech.products.application.usecase.UploadProductCsvUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class UploadProductCsvUseCaseImpl implements UploadProductCsvUseCase {
    private final UploadArchiveGateway uploadArchiveGateway;

    @Override
    public ProductCsvUploadResponse execute(MultipartFile file) {
        return uploadArchiveGateway.uploadProductsFromCsv(file);
    }
}
