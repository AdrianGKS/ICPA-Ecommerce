package com.api.ICPAEcommerce.infra.exception;

import software.amazon.awssdk.core.exception.SdkException;

public class StorageIntegrationException extends RuntimeException {
    public StorageIntegrationException(String s, SdkException e) {
    }
}
