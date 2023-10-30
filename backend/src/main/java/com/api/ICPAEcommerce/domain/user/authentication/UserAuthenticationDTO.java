package com.api.ICPAEcommerce.domain.user.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserAuthenticationDTO(
        @NotBlank
        @Email
        String email,
        @NotBlank
        String password
) {
}
