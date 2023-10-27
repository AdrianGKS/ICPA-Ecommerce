package com.api.ICPAEcommerce.domain.dtos;

import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateWithTokenInputDTO(
        @NotBlank
        String password,

        @NotBlank
        String token
) {
}
