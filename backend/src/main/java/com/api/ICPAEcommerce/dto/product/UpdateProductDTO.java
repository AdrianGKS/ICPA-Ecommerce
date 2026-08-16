package com.api.ICPAEcommerce.dto.product;

import com.api.ICPAEcommerce.domain.file.FileReference;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.util.List;


public record UpdateProductDTO(

        @NotNull
        Long id,
        String code,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        @NotNull
        List<FileReference> files
) {
}
