package com.api.ICPAEcommerce.domain.product;

import jakarta.validation.constraints.NotNull;

public record UpdateProductDTO(

        @NotNull
        Long id,
        String code,

        String name,

        String description,

//        Double price,
//
//        Integer quantity,

        EnumProductCategory enumProductCategory
) {
}
