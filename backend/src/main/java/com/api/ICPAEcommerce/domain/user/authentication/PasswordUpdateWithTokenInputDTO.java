package com.api.ICPAEcommerce.domain.user.authentication;

import jakarta.validation.constraints.NotBlank;

public record PasswordUpdateWithTokenInputDTO(
        @NotBlank
        String password,

        @NotBlank
        String token
) {
}
