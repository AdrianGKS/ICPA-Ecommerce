package com.api.ICPAEcommerce.domain.user.authentication;


public record PasswordTokenPublicDTO(
       String email,
       Long createAtTimestamp
) {
    public PasswordTokenPublicDTO(String email, Long createAtTimestamp) {
        this.email = email;
        this.createAtTimestamp = createAtTimestamp;
    }
}
