package com.api.ICPAEcommerce.domain.product;

import com.api.ICPAEcommerce.domain.file.FileReferenceDTO;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

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