package com.api.ICPAEcommerce.dto.user;

import com.api.ICPAEcommerce.domain.user.EnumUserProfile;
import com.api.ICPAEcommerce.dto.address.AddressDTO;

public record UserResponseDTO(
        Long id,
        String name,
        String email,
        EnumUserProfile profile,
        AddressDTO address
) {
}
