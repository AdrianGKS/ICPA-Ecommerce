package com.api.ICPAEcommerce.dto.product;

import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;


public record UpdateProductDTO(

        @NotNull
        Long id,
        String code,
        String name,
        String description,
        BigDecimal price,
        Integer quantity
) {
}
