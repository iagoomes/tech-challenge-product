package br.com.fiap.postech.products.application.gateway.impl;

import br.com.fiap.postech.products.application.gateway.ProductGateway;
import br.com.fiap.postech.products.infrastructure.gateway.ProductRepositoryGateway;
import br.com.fiap.postech.products.domain.entity.Product;
import br.com.fiap.postech.products.model.ProductCsvUploadResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class ProductGatewayImpl implements ProductGateway {

    private final ProductRepositoryGateway productRepositoryGateway;

    @Override
    public ProductCsvUploadResponse uploadProductsFromCsv(MultipartFile file) {
        List<Product> products = readProductsFromCsv(file);
        Optional<List<Product>> optionalProducts = productRepositoryGateway.saveAll(products);
        if (optionalProducts.isEmpty()) {
            return new ProductCsvUploadResponse("Failed to create products", 0);
        }
        return new ProductCsvUploadResponse("Products successfully created", products.size());
    }

    private List<Product> readProductsFromCsv(MultipartFile file) {
        List<Product> products = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            // Skip the header line
            reader.readLine();
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                String name = data[1];
                String description = data[2];
                BigDecimal price = new BigDecimal(data[3]);
                int stockQuantity = Integer.parseInt(data[4]);
                Product product = new Product(name, description, price, stockQuantity);
                products.add(product);
            }
        } catch (Exception e) {
            // Log error and create custom exception
            throw new RuntimeException("Failed to read CSV file", e);
        }
        return products;
    }
}