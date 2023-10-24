package com.api.ICPAEcommerce.domain.dtos;

import com.api.ICPAEcommerce.domain.enums.EnumUserProfile;

public record RegisterDTO(
        String email,
        String password,
        EnumUserProfile role
) {
}
