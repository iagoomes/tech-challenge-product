package br.com.fiap.postech.products.application.gateway.impl;

import br.com.fiap.postech.products.application.gateway.ProductFileGateway;
import br.com.fiap.postech.products.domain.entity.LoadProduct;
import br.com.fiap.postech.products.exception.FileSaveException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Slf4j
@Component
public class ProductFileGatewayImpl implements ProductFileGateway {

    @Value("${upload.directory}")
    private String directory;

    @Override
    public void saveFile(LoadProduct loadProduct) {
        try {
            Path filePath = Paths.get(directory, LoadProduct.NAME);
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, loadProduct.getBinary());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new FileSaveException("Error saving file");
        }
    }
}
