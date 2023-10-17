package com.api.ICPAEcommerce.domain.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(
    @NotBlank
    String name,

    @NotBlank
    @Email
    String email,

    @NotBlank
    String password,

    @Valid
    AddressDTO address,

    @NotBlank
    String profile
) {}
