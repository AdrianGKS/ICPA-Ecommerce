package com.api.ICPAEcommerce.infra.s3;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.validation.annotation.Validated;

// O uso de 'record' garante que a configuração seja imutável na memória.
@Validated
@ConfigurationProperties(prefix = "aw.storage")
public record StorageProperties(
    @Valid S3 s3
) {
    public record S3(
        @NotBlank String keyId,
        @NotBlank String keySecret,
        @NotBlank String bucket,
        @NotBlank String region,
        @NotBlank String endpoint // Propriedade adicionada para unificar o mapeamento
    ) {}
}