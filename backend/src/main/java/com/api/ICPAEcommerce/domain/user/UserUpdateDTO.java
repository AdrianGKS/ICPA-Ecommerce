package com.api.ICPAEcommerce.domain.user;

import com.api.ICPAEcommerce.domain.user.address.AddressDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

public record UserUpdateDTO(

    String name,

    @Email
    String email,

    @Valid
    AddressDTO address,

    EnumUserProfile profile
) {}
