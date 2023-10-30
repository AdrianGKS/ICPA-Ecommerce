package com.api.ICPAEcommerce.domain.user;

import lombok.Getter;

@Getter
public enum EnumUserProfile {

    ADMIN("admin"),
    USER("user");

    private final String profile;
    EnumUserProfile(String profile) {
        this.profile = profile;
    }

}
