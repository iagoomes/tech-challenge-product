package br.com.fiap.postech.products.application.gateway.impl;


import br.com.fiap.postech.products.api.ProductManagementApiDelegate;
import br.com.fiap.postech.products.model.Product;
import br.com.fiap.postech.products.model.ProductCsvUploadResponse;
import br.com.fiap.postech.products.application.usecase.UploadProductCsvUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Component
@RequiredArgsConstructor
public class ProductResourceGateway implements ProductManagementApiDelegate {

    private final UploadProductCsvUseCase uploadProductCsvUseCase;

    @Override
    public CompletableFuture<ResponseEntity<Void>> deleteProductById(Long id) {
        return ProductManagementApiDelegate.super.deleteProductById(id);
    }

    @Override
    public CompletableFuture<ResponseEntity<List<Product>>> getAllProducts() {
        return ProductManagementApiDelegate.super.getAllProducts();
    }

    @Override
    public CompletableFuture<ResponseEntity<Product>> getProductById(Long id) {
        return ProductManagementApiDelegate.super.getProductById(id);
    }

    @Override
    public CompletableFuture<ResponseEntity<Product>> updateProductById(Long id, Product product) {
        return ProductManagementApiDelegate.super.updateProductById(id, product);
    }

    @Override
    public CompletableFuture<ResponseEntity<ProductCsvUploadResponse>> uploadProductCsv(MultipartFile file) {
        ProductCsvUploadResponse execute = uploadProductCsvUseCase.execute(file);
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(execute));
    }
}
