package br.com.fiap.shopsphere.ms.pagamento.controller.handlers;

import br.com.fiap.shopsphere.ms.pagamento.exception.RecursoNaoEncontradoException;
import br.com.fiap.shopsphere.ms.pagamento.exception.ErroResponse;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GlobalExceptionHandlerTest {

    private GlobalExceptionHandler handler;

    @BeforeEach
    void setup() {
        handler = new GlobalExceptionHandler();
    }

    @Test
    void deveTratarMethodArgumentNotValidException() {
        BindingResult bindingResult = mock(BindingResult.class);
        List<FieldError> fieldErrors = List.of(
                new FieldError("obj", "campo1", "mensagem1"),
                new FieldError("obj", "campo2", "mensagem2")
        );
        when(bindingResult.getFieldErrors()).thenReturn(fieldErrors);

        MethodArgumentNotValidException ex = new MethodArgumentNotValidException(null, bindingResult);

        ResponseEntity<Map<String, String>> response = handler.handleValidationErrors(ex);

        assertEquals(400, response.getStatusCodeValue());
        Map<String, String> body = response.getBody();
        assertNotNull(body);
        assertEquals("mensagem1", body.get("campo1"));
        assertEquals("mensagem2", body.get("campo2"));
    }

    @Test
    void deveTratarConstraintViolationException() {
        ConstraintViolation<?> violation1 = mock(ConstraintViolation.class);
        ConstraintViolation<?> violation2 = mock(ConstraintViolation.class);

        Path path1 = mock(Path.class);
        Path path2 = mock(Path.class);

        when(path1.toString()).thenReturn("param1");
        when(path2.toString()).thenReturn("param2");

        when(violation1.getPropertyPath()).thenReturn(path1);
        when(violation1.getMessage()).thenReturn("mensagem1");

        when(violation2.getPropertyPath()).thenReturn(path2);
        when(violation2.getMessage()).thenReturn("mensagem2");

        Set<ConstraintViolation<?>> violations = new HashSet<>(Set.of(violation1, violation2));
        ConstraintViolationException ex = new ConstraintViolationException(violations);

        ResponseEntity<Map<String, String>> response = handler.handleConstraintViolation(ex);

        assertEquals(400, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertEquals("mensagem1", response.getBody().get("param1"));
        assertEquals("mensagem2", response.getBody().get("param2"));
    }

    @Test
    void deveTratarErroGenerico() {
        Exception ex = new RuntimeException("erro inesperado");

        ResponseEntity<Map<String, String>> response = handler.handleGenericError(ex);

        assertEquals(500, response.getStatusCodeValue());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().get("erro").contains("erro inesperado"));
    }

    @Test
    void deveTratarRecursoNaoEncontradoException() {
        String mensagem = "Pagamento não encontrado";
        RecursoNaoEncontradoException ex = new RecursoNaoEncontradoException(mensagem);

        ResponseEntity<?> response = handler.handleRecursoNaoEncontrado(ex);

        assertEquals(404, response.getStatusCodeValue());

        Object responseBody = response.getBody();
        assertNotNull(responseBody);
        assertInstanceOf(ErroResponse.class, responseBody);

        ErroResponse body = (ErroResponse) responseBody;
        assertEquals(404, body.status());
        assertEquals(mensagem, body.mensagem());
        assertNotNull(body.timestamp());
    }
}