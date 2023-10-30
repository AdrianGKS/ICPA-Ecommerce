package com.api.ICPAEcommerce.domain.image;

import jakarta.validation.constraints.NotBlank;

public record ImageDTO(

        @NotBlank
        String image
) {
        public ImageDTO(Image image) {
                this(image.getImage());
        }
}
