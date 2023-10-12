package com.api.ICPAEcommerce.domain.dtos;

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
