package com.api.ICPAEcommerce.infra.exception;

// Agora ela aceita qualquer causa (Throwable), ignorando se é da AWS, Azure ou GCP
public class StorageIntegrationException extends RuntimeException {
    public StorageIntegrationException(String message, Throwable cause) {
        super(message, cause); // Repassando para a superclasse para manter o Stack Trace
    }
}