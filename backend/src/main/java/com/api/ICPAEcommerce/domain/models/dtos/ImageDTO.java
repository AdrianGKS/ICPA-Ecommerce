package com.api.ICPAEcommerce.domain.models.dtos;

import com.api.ICPAEcommerce.domain.models.Image;
import jakarta.validation.constraints.NotBlank;

public record ImageDTO(

        Long id,
        @NotBlank
        String image
) {
        public ImageDTO(Image image) {
                this(image.getId(), image.getImage());
        }
}
