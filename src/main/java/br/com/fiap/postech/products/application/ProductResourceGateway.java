package br.com.fiap.postech.products.application;

import br.com.fiap.postech.products.api.ProductManagementApiDelegate;
import br.com.fiap.postech.products.application.usecase.CreateProductUseCase;
import br.com.fiap.postech.products.application.usecase.DeleteProductByIdUseCase;
import br.com.fiap.postech.products.application.usecase.GetAllProductsUseCase;
import br.com.fiap.postech.products.application.usecase.GetProductByIdUseCase;
import br.com.fiap.postech.products.application.usecase.ProductBatchUploaderUseCase;
import br.com.fiap.postech.products.application.usecase.UpdateProductStockUseCase;
import br.com.fiap.postech.products.application.usecase.UpdateProductUseCase;
import br.com.fiap.postech.products.domain.entity.LoadProduct;
import br.com.fiap.postech.products.model.ProductApiModel;
import br.com.fiap.postech.products.model.ProductCsvUploadResponse;
import br.com.fiap.postech.products.model.UpdateProductStockRequest;
import br.com.fiap.postech.products.model.UpdateProductStockRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Component
@RequiredArgsConstructor
public class ProductResourceGateway implements ProductManagementApiDelegate {

    private final ProductBatchUploaderUseCase productBatchUploaderUseCase;
    private final CreateProductUseCase createProductUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final GetProductByIdUseCase getProductByIdUseCase;
    private final GetAllProductsUseCase getAllProductsUseCase;
    private final DeleteProductByIdUseCase deleteProductByIdUseCase;
    private final UpdateProductStockUseCase updateProductStockUseCase;

    @Override
    public CompletableFuture<ResponseEntity<Void>> deleteProductById(Long id) {
        return CompletableFuture.supplyAsync(() -> {
            deleteProductByIdUseCase.execute(id);
            return ResponseEntity.noContent().build();
        });
    }

    @Override
    public CompletableFuture<ResponseEntity<List<ProductApiModel>>> getAllProducts() {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(getAllProductsUseCase.execute()));
    }

    @Override
    public CompletableFuture<ResponseEntity<ProductApiModel>> getProductById(Long id) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(getProductByIdUseCase.execute(id)));
    }

    @Override
    public CompletableFuture<ResponseEntity<ProductApiModel>> updateProductById(Long id, @Valid ProductApiModel productApiModel) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(updateProductUseCase.execute(id, productApiModel)));
    }

    @Override
    public CompletableFuture<ResponseEntity<ProductApiModel>> createProduct(@Valid ProductApiModel productApiModel) {
        return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(createProductUseCase.execute(productApiModel)));
    }

    @Override
    public CompletableFuture<ResponseEntity<Void>> updateProductStock(List<@Valid UpdateProductStockRequest> updateProductStockRequest) {
        return CompletableFuture.supplyAsync(() -> {
            updateProductStockUseCase.execute(updateProductStockRequest);
            return ResponseEntity.ok().build();
        });
    }

    @Override
    public CompletableFuture<ResponseEntity<ProductCsvUploadResponse>> uploadProductCsv(MultipartFile file) {
        try {
            LoadProduct load = new LoadProduct(file.getBytes());
            return CompletableFuture.supplyAsync(() -> ResponseEntity.ok(productBatchUploaderUseCase.execute(load)));
        } catch (IOException e) {
            log.error(e.getMessage(), e);
            return CompletableFuture.supplyAsync(() -> ResponseEntity.badRequest().build());
        }
    }
}
