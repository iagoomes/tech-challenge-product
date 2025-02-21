package br.com.fiap.postech.products.exception;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.context.request.WebRequest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class GlobalExceptionHandlerTest {

    @Test
    void handleInvalidAttributeException_ReturnsBadRequest() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        InvalidAttributeException ex = new InvalidAttributeException("Invalid attribute");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("Invalid attribute description");

        ResponseEntity<?> response = handler.handleInvalidAttributeException(ex, request);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDetails errorDetails = (ErrorDetails) response.getBody();
        assertNotNull(errorDetails);
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorDetails.getStatus());
        assertEquals("Invalid attribute", errorDetails.getMessage());
        assertEquals("Invalid attribute description", errorDetails.getDetails());
    }

    @Test
    void handleGlobalException_ReturnsInternalServerError() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        Exception ex = new Exception("Internal server error");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("Internal server error description");

        ResponseEntity<?> response = handler.handleGlobalException(ex, request);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        ErrorDetails errorDetails = (ErrorDetails) response.getBody();
        assertNotNull(errorDetails);
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR.value(), errorDetails.getStatus());
        assertEquals("Internal server error", errorDetails.getMessage());
        assertEquals("Internal server error description", errorDetails.getDetails());
    }

    @Test
    void handleProductNotFoundException_ReturnsNotFound() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        ProductNotFoundException ex = new ProductNotFoundException("Product not found");
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("Product not found description");

        ResponseEntity<?> response = handler.handleProductNotFoundException(ex, request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorDetails errorDetails = (ErrorDetails) response.getBody();
        assertNotNull(errorDetails);
        assertEquals(HttpStatus.NOT_FOUND.value(), errorDetails.getStatus());
        assertEquals("Product not found", errorDetails.getMessage());
        assertEquals("Product not found description", errorDetails.getDetails());
    }

    @Test
    void handleNoResourceFoundException_ReturnsNotFound() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        WebRequest request = mock(WebRequest.class);
        when(request.getDescription(false)).thenReturn("Resource not found description");

        ResponseEntity<?> response = handler.handleNoResourceFoundException(request);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        ErrorDetails errorDetails = (ErrorDetails) response.getBody();
        assertNotNull(errorDetails);
        assertEquals(HttpStatus.NOT_FOUND.value(), errorDetails.getStatus());
        assertEquals("Resource not found", errorDetails.getMessage());
        assertEquals("Resource not found description", errorDetails.getDetails());
    }

    @Test
    void handleMethodArgumentNotValidException_ReturnsBadRequest() {
        GlobalExceptionHandler handler = new GlobalExceptionHandler();
        MethodArgumentNotValidException ex = mock(MethodArgumentNotValidException.class);
        BindingResult bindingResult = mock(BindingResult.class);
        when(ex.getBindingResult()).thenReturn(bindingResult);
        when(bindingResult.getFieldErrors()).thenReturn(List.of(
                new FieldError("productApiModel", "name", "must not be empty")
        ));

        ResponseEntity<?> response = handler.handleMethodArgumentNotValidException(ex);

        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        ErrorDetails errorDetails = (ErrorDetails) response.getBody();
        assertNotNull(errorDetails);
        assertEquals(HttpStatus.BAD_REQUEST.value(), errorDetails.getStatus());
        assertEquals("Validation Failed", errorDetails.getMessage());
        assertEquals("name: must not be empty", errorDetails.getDetails());
    }
}
