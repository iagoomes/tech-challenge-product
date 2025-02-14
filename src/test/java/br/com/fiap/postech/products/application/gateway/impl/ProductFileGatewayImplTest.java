package br.com.fiap.postech.products.application.gateway.impl;

import br.com.fiap.postech.products.domain.entity.LoadProduct;
import br.com.fiap.postech.products.exception.FileSaveException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockedStatic;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;

import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ProductFileGatewayImplTest {

    private ProductFileGatewayImpl productFileGatewayImpl;

    @Mock
    private LoadProduct loadProduct;

    @Value("${upload.directory}")
    private String directory;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        productFileGatewayImpl = new ProductFileGatewayImpl();

        // Use reflection to set the private field
        Field directoryField = ProductFileGatewayImpl.class.getDeclaredField("directory");
        directoryField.setAccessible(true);
        directoryField.set(productFileGatewayImpl, "test-directory");
    }

    @Test
    void saveFile_shouldSaveFileSuccessfully() throws Exception {
        when(loadProduct.getName()).thenReturn("test-file.txt");
        when(loadProduct.getBinary()).thenReturn("file content".getBytes());

        productFileGatewayImpl.saveFile(loadProduct);

        Path filePath = Paths.get("test-directory", "test-file.txt");
        assertTrue(Files.exists(filePath));
        assertEquals("file content", Files.readString(filePath));

        Files.deleteIfExists(filePath);
    }

    @Test
    void saveFile_shouldThrowFileSaveException_whenIOExceptionOccurs() throws Exception {
        when(loadProduct.getName()).thenReturn("test-file.txt");
        when(loadProduct.getBinary()).thenReturn("file content".getBytes());

        Path filePath = Paths.get("test-directory", "test-file.txt");

        try (MockedStatic<Files> mockedFiles = Mockito.mockStatic(Files.class)) {
            mockedFiles.when(() -> Files.write(eq(filePath), any(byte[].class)))
                    .thenThrow(new IOException("Failed to write file"));

            FileSaveException exception = assertThrows(FileSaveException.class, () -> {
                productFileGatewayImpl.saveFile(loadProduct);
            });

            assertEquals("Error saving file", exception.getMessage());
        }
    }


    @Test
    void saveFile_shouldThrowFileSaveException_whenLoadProductIsNull() {
        FileSaveException exception = assertThrows(FileSaveException.class, () -> {
            productFileGatewayImpl.saveFile(null);
        });

        assertEquals("Error saving file", exception.getMessage());
    }
}