package com.api.ICPAEcommerce.infra.exception;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.net.URI;
import java.util.List;

@RestControllerAdvice
public class ExceptionsHandler {

    // Trata ambos os erros de "não encontrado" com o status 404 correto
    @ExceptionHandler({EntityNotFoundException.class, EmptyResultDataAccessException.class})
    public ProblemDetail handleNotFound(Exception ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.NOT_FOUND, "Recurso não encontrado.");
        problemDetail.setTitle("Recurso não encontrado");
        return problemDetail;
    }

    // Tratamento padronizado para erros de nuvem (Bad Gateway indica falha em serviço externo)
    @ExceptionHandler(StorageIntegrationException.class)
    public ProblemDetail handleStorageIntegration(StorageIntegrationException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_GATEWAY, ex.getMessage());
        problemDetail.setTitle("Erro de Integração com Storage");
        return problemDetail;
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ProblemDetail handleMethodArgumentNotValid(MethodArgumentNotValidException ex) {
        ProblemDetail problemDetail = ProblemDetail.forStatusAndDetail(HttpStatus.BAD_REQUEST, "A requisição possui campos inválidos.");
        problemDetail.setTitle("Erro de Validação");

        List<ErrorData> errors = ex.getFieldErrors().stream()
                .map(ErrorData::new)
                .toList();

        // Adicionamos a lista de campos com erro ao payload padrão do ProblemDetail
        problemDetail.setProperty("invalid_params", errors);

        return problemDetail;
    }

    private record ErrorData(String field, String message) {
        public ErrorData(FieldError error) {
            this(error.getField(), error.getDefaultMessage());
        }
    }
}