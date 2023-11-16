package com.api.ICPAEcommerce.domain.file;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record FileReferenceDTO(
        @NotNull
        Long id,
        @NotNull @NotBlank
        String name,
        @NotNull @NotBlank
        String contentType,
        @NotNull
        Long contentLength,
        @NotNull
        Type type
) {
}
