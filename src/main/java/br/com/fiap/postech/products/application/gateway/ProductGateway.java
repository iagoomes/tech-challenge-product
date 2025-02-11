package br.com.fiap.postech.products.application.gateway;

import br.com.fiap.postech.products.model.ProductCsvUploadResponse;
import org.springframework.web.multipart.MultipartFile;

public interface ProductGateway {
    ProductCsvUploadResponse uploadProductsFromCsv(MultipartFile file);
}
