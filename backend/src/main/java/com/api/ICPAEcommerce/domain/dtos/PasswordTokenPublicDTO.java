package com.api.ICPAEcommerce.domain.dtos;


public record PasswordTokenPublicDTO(
       String email,
       Long createAtTimestamp
) {
    public PasswordTokenPublicDTO(String email, Long createAtTimestamp) {
        this.email = email;
        this.createAtTimestamp = createAtTimestamp;
    }
}
