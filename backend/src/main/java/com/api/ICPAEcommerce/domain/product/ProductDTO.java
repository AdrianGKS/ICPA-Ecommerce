package com.api.ICPAEcommerce.domain.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record ProductDTO (

    @NotBlank
    String code,

    @NotBlank
    String name,

    @NotBlank
    String description,

    @DecimalMin("0.01")
    double price,

    @Min(1)
    int quantity,

    @NotNull
    EnumProductCategory enumProductCategory
){
}