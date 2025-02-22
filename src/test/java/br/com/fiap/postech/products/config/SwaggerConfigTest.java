package br.com.fiap.postech.products.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class SwaggerConfigTest {

    private final SwaggerConfig swaggerConfig = new SwaggerConfig();

    @Test
    void customOpenAPI_ReturnsOpenAPIWithCorrectInfo() {
        OpenAPI openAPI = swaggerConfig.customOpenAPI();
        Info info = openAPI.getInfo();

        assertEquals("Products Management API", info.getTitle());
        assertEquals("1.0", info.getVersion());
        assertEquals("API for managing products", info.getDescription());
    }

    @Test
    void customOpenAPI_ReturnsNonNullOpenAPI() {
        OpenAPI openAPI = swaggerConfig.customOpenAPI();
        assertNotNull(openAPI);
    }

    @Test
    void customOpenAPI_InfoIsNotNull() {
        OpenAPI openAPI = swaggerConfig.customOpenAPI();
        assertNotNull(openAPI.getInfo());
    }
}