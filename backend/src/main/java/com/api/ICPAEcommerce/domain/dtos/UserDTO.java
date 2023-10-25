package com.api.ICPAEcommerce.domain.dtos;

import com.api.ICPAEcommerce.domain.enums.EnumUserProfile;
import com.api.ICPAEcommerce.domain.models.Address;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

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

    @NotNull
    EnumUserProfile profile
) {}
