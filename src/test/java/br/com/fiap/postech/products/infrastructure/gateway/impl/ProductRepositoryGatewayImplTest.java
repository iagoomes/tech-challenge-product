
package br.com.fiap.postech.products.infrastructure.gateway.impl;

import br.com.fiap.postech.products.domain.entity.Product;
import br.com.fiap.postech.products.exception.ProductNotFoundException;
import br.com.fiap.postech.products.infrastructure.mapper.ProductMapper;
import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import br.com.fiap.postech.products.infrastructure.persistence.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductRepositoryGatewayImplTest {

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductRepositoryGatewayImpl productRepositoryGateway;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveAll_ReturnsSavedProducts() {
        List<Product> products = List.of(new Product());
        List<ProductEntity> productEntities = List.of(new ProductEntity());
        when(productMapper.toEntity(any(Product.class))).thenReturn(new ProductEntity());
        when(productRepository.saveAll(anyList())).thenReturn(productEntities);
        when(productMapper.toModel(any(ProductEntity.class))).thenReturn(new Product());

        Optional<List<Product>> result = productRepositoryGateway.saveAll(products);

        assertTrue(result.isPresent());
        assertEquals(products.size(), result.get().size());
    }

    @Test
    void save_ReturnsSavedProduct() {
        Product product = new Product();
        ProductEntity productEntity = new ProductEntity();
        when(productMapper.toEntity(any(Product.class))).thenReturn(productEntity);
        when(productRepository.save(any(ProductEntity.class))).thenReturn(productEntity);
        when(productMapper.toModel(any(ProductEntity.class))).thenReturn(product);

        Product result = productRepositoryGateway.save(product);

        assertNotNull(result);
    }

    @Test
    void findById_ReturnsProduct() {
        Long id = 1L;
        ProductEntity productEntity = new ProductEntity();
        when(productRepository.findById(id)).thenReturn(Optional.of(productEntity));
        when(productMapper.toModel(any(ProductEntity.class))).thenReturn(new Product());

        Product result = productRepositoryGateway.findById(id);

        assertNotNull(result);
    }

    @Test
    void findById_ThrowsProductNotFoundException() {
        Long id = 1L;
        when(productRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> productRepositoryGateway.findById(id));
    }

    @Test
    void findAll_ReturnsAllProducts() {
        List<ProductEntity> productEntities = List.of(new ProductEntity());
        when(productRepository.findAll()).thenReturn(productEntities);
        when(productMapper.toModel(any(ProductEntity.class))).thenReturn(new Product());

        List<Product> result = productRepositoryGateway.findAll();

        assertNotNull(result);
        assertEquals(productEntities.size(), result.size());
    }

    @Test
    void existsById_ReturnsTrue() {
        Long id = 1L;
        when(productRepository.existsById(id)).thenReturn(true);

        boolean result = productRepositoryGateway.existsById(id);

        assertTrue(result);
    }

    @Test
    void existsById_ThrowsRuntimeException() {
        Long id = 1L;
        when(productRepository.existsById(id)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> productRepositoryGateway.existsById(id));
    }

    @Test
    void deleteById_DeletesProduct() {
        Long id = 1L;
        doNothing().when(productRepository).deleteById(id);
        productRepositoryGateway.deleteById(id);

        verify(productRepository, times(1)).deleteById(id);
    }
}
