package com.api.ICPAEcommerce.domain.models.dtos;

import jakarta.validation.constraints.NotBlank;

public record UserChangePasswordDTO(
        @NotBlank
        String password
) {
}
