package br.com.fiap.postech.products.infrastructure.gateway.impl;

import br.com.fiap.postech.products.domain.entity.Product;
import br.com.fiap.postech.products.infrastructure.persistence.ProductEntity;
import br.com.fiap.postech.products.infrastructure.gateway.ProductRepositoryGateway;
import br.com.fiap.postech.products.infrastructure.mapper.ProductMapper;
import br.com.fiap.postech.products.infrastructure.persistence.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductRepositoryGatewayImpl implements ProductRepositoryGateway {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    @Override
    public Optional<List<Product>> saveAll(List<Product> product) {
        List<ProductEntity> productEntities = product.stream().map(productMapper::toEntity).toList();
        var entities = productRepository.saveAll(productEntities);
        return Optional.of(entities.stream().map(productMapper::toModel).toList());
    }

    @Override
    public Product save(Product product) {
        ProductEntity productEntity = productMapper.toEntity(product);
        ProductEntity entity = productRepository.save(productEntity);
        return productMapper.toModel(entity);
    }

    @Override
    public Optional<Product> findById(Long id) {
        ProductEntity entity = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        return Optional.of(productMapper.toModel(entity));
    }

    @Override
    public boolean existsById(Long id) {
        return productRepository.existsById(id);
    }

}
