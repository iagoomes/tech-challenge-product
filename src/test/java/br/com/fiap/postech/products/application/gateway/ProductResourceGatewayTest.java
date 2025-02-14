package br.com.fiap.postech.products.application.gateway;

import br.com.fiap.postech.products.application.ProductResourceGateway;
import br.com.fiap.postech.products.application.usecase.CreateProductUseCase;
import br.com.fiap.postech.products.application.usecase.DeleteProductByIdUseCase;
import br.com.fiap.postech.products.application.usecase.GetAllProductsUseCase;
import br.com.fiap.postech.products.application.usecase.GetProductByIdUseCase;
import br.com.fiap.postech.products.application.usecase.ProductBatchUploaderUseCase;
import br.com.fiap.postech.products.application.usecase.UpdateProductUseCase;
import br.com.fiap.postech.products.model.ProductApiModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.concurrent.CompletableFuture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ProductResourceGatewayTest {

    private ProductResourceGateway productResourceGateway;
    private ProductBatchUploaderUseCase productBatchUploaderUseCase;
    private CreateProductUseCase createProductUseCase;
    private UpdateProductUseCase updateProductUseCase;
    private GetProductByIdUseCase getProductByIdUseCase;
    private GetAllProductsUseCase getAllProductsUseCase;
    private DeleteProductByIdUseCase deleteProductByIdUseCase;

    @BeforeEach
    void setUp() {
        productBatchUploaderUseCase = mock(ProductBatchUploaderUseCase.class);
        createProductUseCase = mock(CreateProductUseCase.class);
        updateProductUseCase = mock(UpdateProductUseCase.class);
        getProductByIdUseCase = mock(GetProductByIdUseCase.class);
        getAllProductsUseCase = mock(GetAllProductsUseCase.class);
        deleteProductByIdUseCase = mock(DeleteProductByIdUseCase.class);
        productResourceGateway = new ProductResourceGateway(productBatchUploaderUseCase, createProductUseCase, updateProductUseCase, getProductByIdUseCase, getAllProductsUseCase, deleteProductByIdUseCase);
    }

    @Test
    void deleteProductById_ReturnsNoContent() {
        Long productId = 1L;
        doNothing().when(deleteProductByIdUseCase).execute(productId);

        CompletableFuture<ResponseEntity<Void>> response = productResourceGateway.deleteProductById(productId);

        assertEquals(ResponseEntity.noContent().build(), response.join());
    }

    @Test
    void getAllProducts_ReturnsProductList() {
        List<ProductApiModel> products = List.of(new ProductApiModel());
        when(getAllProductsUseCase.execute()).thenReturn(products);

        CompletableFuture<ResponseEntity<List<ProductApiModel>>> response = productResourceGateway.getAllProducts();

        assertEquals(ResponseEntity.ok(products), response.join());
    }

    @Test
    void getProductById_ReturnsProduct() {
        Long productId = 1L;
        ProductApiModel product = new ProductApiModel();
        when(getProductByIdUseCase.execute(productId)).thenReturn(product);

        CompletableFuture<ResponseEntity<ProductApiModel>> response = productResourceGateway.getProductById(productId);

        assertEquals(ResponseEntity.ok(product), response.join());
    }

    @Test
    void updateProductById_ReturnsUpdatedProduct() {
        Long productId = 1L;
        ProductApiModel product = new ProductApiModel();
        when(updateProductUseCase.execute(productId, product)).thenReturn(product);

        CompletableFuture<ResponseEntity<ProductApiModel>> response = productResourceGateway.updateProductById(productId, product);

        assertEquals(ResponseEntity.ok(product), response.join());
    }

    @Test
    void createProduct_ReturnsCreatedProduct() {
        ProductApiModel product = new ProductApiModel();
        when(createProductUseCase.execute(product)).thenReturn(product);

        CompletableFuture<ResponseEntity<ProductApiModel>> response = productResourceGateway.createProduct(product);

        assertEquals(ResponseEntity.ok(product), response.join());
    }

//    @Test
//    void uploadProductCsv_ReturnsUploadResponse() {
//        MultipartFile file = mock(MultipartFile.class);
//        ProductCsvUploadResponse response = new ProductCsvUploadResponse();
//        when(productBatchUploaderUseCase.execute(file)).thenReturn(response);
//
//        CompletableFuture<ResponseEntity<ProductCsvUploadResponse>> result = productResourceGateway.uploadProductCsv(file);
//
//        assertEquals(ResponseEntity.ok(response), result.join());
//    }
}