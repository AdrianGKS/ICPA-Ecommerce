package com.api.ICPAEcommerce.domain.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserChangePasswordDTO(
        @NotBlank
        String password
) {
}
