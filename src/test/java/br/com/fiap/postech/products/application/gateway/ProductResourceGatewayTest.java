package br.com.fiap.postech.products.application.gateway;

import br.com.fiap.postech.products.application.ProductResourceGateway;
import br.com.fiap.postech.products.application.usecase.CreateProductUseCase;
import br.com.fiap.postech.products.application.usecase.DeleteProductByIdUseCase;
import br.com.fiap.postech.products.application.usecase.GetAllProductsUseCase;
import br.com.fiap.postech.products.application.usecase.GetProductByIdUseCase;
import br.com.fiap.postech.products.application.usecase.ProductBatchUploaderUseCase;
import br.com.fiap.postech.products.application.usecase.UpdateProductUseCase;
import br.com.fiap.postech.products.domain.entity.LoadProduct;
import br.com.fiap.postech.products.model.ProductApiModel;
import br.com.fiap.postech.products.model.ProductCsvUploadResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Test
    void uploadProductCsv_shouldReturnOkResponse_whenFileIsUploadedSuccessfully() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        byte[] fileContent = "file content".getBytes();
        when(file.getBytes()).thenReturn(fileContent);

        LoadProduct loadProduct = new LoadProduct(fileContent);
        ProductCsvUploadResponse response = new ProductCsvUploadResponse();
        when(productBatchUploaderUseCase.execute(any(LoadProduct.class))).thenReturn(response);

        CompletableFuture<ResponseEntity<ProductCsvUploadResponse>> result = productResourceGateway.uploadProductCsv(file);

        assertEquals(ResponseEntity.ok().body(response), result.join());
        verify(productBatchUploaderUseCase).execute(any(LoadProduct.class));
    }

    @Test
    void uploadProductCsv_shouldReturnBadRequest_whenIOExceptionOccurs() throws Exception {
        MultipartFile file = mock(MultipartFile.class);
        when(file.getBytes()).thenThrow(new IOException("Failed to read file"));

        CompletableFuture<ResponseEntity<ProductCsvUploadResponse>> result = productResourceGateway.uploadProductCsv(file);

        assertEquals(ResponseEntity.badRequest().build(), result.join());
        verify(productBatchUploaderUseCase, never()).execute(any(LoadProduct.class));
    }
}