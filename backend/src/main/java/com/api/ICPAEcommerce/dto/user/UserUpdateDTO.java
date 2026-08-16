package com.api.ICPAEcommerce.dto.user;

import com.api.ICPAEcommerce.dto.address.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

public record UserUpdateDTO(

    String name,

    @Email
    String email,

    @Valid
    AddressDTO address
) {}
