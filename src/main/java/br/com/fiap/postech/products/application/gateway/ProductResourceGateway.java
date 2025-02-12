package br.com.fiap.postech.products.application.gateway;


import br.com.fiap.postech.products.api.ProductManagementApiDelegate;
import br.com.fiap.postech.products.application.usecase.CreateProductUseCase;
import br.com.fiap.postech.products.application.usecase.UpdateProductUseCase;
import br.com.fiap.postech.products.application.usecase.UploadProductCsvUseCase;
import br.com.fiap.postech.products.model.ProductCsvUploadResponse;
import br.com.fiap.postech.products.model.ProductApiModel;
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
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;

    @Override
    public CompletableFuture<ResponseEntity<Void>> deleteProductById(Long id) {
        return ProductManagementApiDelegate.super.deleteProductById(id);
    }

    @Override
    public CompletableFuture<ResponseEntity<List<ProductApiModel>>> getAllProducts() {
        return ProductManagementApiDelegate.super.getAllProducts();
    }

    @Override
    public CompletableFuture<ResponseEntity<ProductApiModel>> getProductById(Long id) {
        return ProductManagementApiDelegate.super.getProductById(id);
    }

    @Override
    public CompletableFuture<ResponseEntity<ProductApiModel>> updateProductById(Long id, ProductApiModel ProductApiModel) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(updateProductUseCase.execute(id, ProductApiModel)));
    }

    @Override
    public CompletableFuture<ResponseEntity<ProductApiModel>> createProduct(ProductApiModel ProductApiModel) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(createProductUseCase.execute(ProductApiModel)));
    }

    @Override
    public CompletableFuture<ResponseEntity<ProductCsvUploadResponse>> uploadProductCsv(MultipartFile file) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(uploadProductCsvUseCase.execute(file)));
    }
}
