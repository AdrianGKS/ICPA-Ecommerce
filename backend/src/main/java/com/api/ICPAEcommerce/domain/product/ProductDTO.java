package com.api.ICPAEcommerce.domain.product;

import com.api.ICPAEcommerce.domain.file.FileReference;
import jakarta.validation.constraints.*;

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
    EnumProductCategory enumProductCategory,

    @NotNull
    FileReference file
){
}