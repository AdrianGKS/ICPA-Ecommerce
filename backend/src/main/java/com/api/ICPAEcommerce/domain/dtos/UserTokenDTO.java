package com.api.ICPAEcommerce.domain.dtos;

import com.api.ICPAEcommerce.domain.models.User;
import org.springframework.http.ResponseEntity;

public record UserTokenDTO(
        ResponseEntity user,
        String token
) {
}
