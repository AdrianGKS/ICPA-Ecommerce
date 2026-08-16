package com.api.ICPAEcommerce.dto.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PasswordResetInputDTO(
        @NotBlank
        @Email
        String email
) {
}
