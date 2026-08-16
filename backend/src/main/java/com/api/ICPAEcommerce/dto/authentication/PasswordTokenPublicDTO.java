package com.api.ICPAEcommerce.dto.authentication;


public record PasswordTokenPublicDTO(
       String email,
       Long createAtTimestamp
) {
}
