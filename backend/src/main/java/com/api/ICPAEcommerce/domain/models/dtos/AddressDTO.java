package com.api.ICPAEcommerce.domain.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record AddressDTO(

        @NotBlank
        String street,
        @NotBlank
        String number,
        @NotBlank
        String city,
        @NotBlank
        String neighborhood,
        @NotBlank
        String state,
        @NotBlank
        @Pattern(regexp = "\\d{8}")
        String cep,
        String complement
) {
}

