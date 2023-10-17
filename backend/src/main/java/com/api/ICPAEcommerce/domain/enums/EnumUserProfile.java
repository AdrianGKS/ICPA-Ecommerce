package com.api.ICPAEcommerce.domain.enums;

import lombok.Getter;

@Getter
public enum EnumUserProfile {

    ROLE_ADMIN("admin"),
    ROLE_USER("user");

    private final String profile;
    EnumUserProfile(String profile) {
        this.profile = profile;
    }

}
