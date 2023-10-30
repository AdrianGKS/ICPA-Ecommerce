package com.api.ICPAEcommerce.domain.user.authentication;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record PasswordResetInputDTO(
        @NotBlank
        @Email
        String email
) {
}
