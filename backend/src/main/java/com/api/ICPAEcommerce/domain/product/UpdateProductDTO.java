package com.api.ICPAEcommerce.domain.product;

import com.api.ICPAEcommerce.domain.file.FileReference;
import jakarta.validation.constraints.NotNull;


public record UpdateProductDTO(

        @NotNull
        Long id,
        String code,
        String name,
        String description,
        double price,
        int quantity,
        @NotNull
        FileReference file
) {
}
