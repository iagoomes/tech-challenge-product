package br.com.fiap.postech.products.application.usecase;

import br.com.fiap.postech.products.model.ProductCsvUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface UploadProductCsvUseCase {
    ProductCsvUploadResponse execute(MultipartFile file);
}
