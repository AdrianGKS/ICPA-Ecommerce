package com.api.ICPAEcommerce.dto.authentication;

import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateWithTokenInputDTO(
        @NotBlank
        String password,

        @NotBlank
        String token
) {
}
