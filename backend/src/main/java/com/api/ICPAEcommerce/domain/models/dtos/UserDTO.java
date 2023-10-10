package com.api.ICPAEcommerce.domain.models.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record UserDTO(

    Long id,
    @NotBlank
    String name,
    @NotBlank
    @Email
    String email,
    @NotBlank
    String password,
    @Valid
    AddressDTO address) {}
