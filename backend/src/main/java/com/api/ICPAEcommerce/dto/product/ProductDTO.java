package com.api.ICPAEcommerce.dto.product;

import com.api.ICPAEcommerce.domain.product.EnumProductCategory;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ProductDTO (

    @NotBlank
    String code,

    @NotBlank
    String name,

    @NotBlank
    String description,

    @DecimalMin("0.01")
    BigDecimal price,

    @Min(1)
    Integer quantity,

    @NotNull
    EnumProductCategory enumProductCategory
){
}