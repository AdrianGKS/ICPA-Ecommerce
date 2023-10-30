package com.api.ICPAEcommerce.domain.user;

import com.api.ICPAEcommerce.domain.user.address.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record UserRegisterDTO(
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
