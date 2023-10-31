package com.api.ICPAEcommerce.domain.product;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
public class ProductDTO {

    @NotBlank
    String code;

    @NotBlank
    String name;

    @NotBlank
    String description;

    @DecimalMin("0.01")
    Double price;

    @Min(1)
    Integer quantity;

    @NotNull
    EnumProductCategory enumProductCategory;


    public ProductDTO(Product product) {
        this.code = product.getCode();
        this.name = product.getName();
        this.description = product.getDescription();
        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.enumProductCategory = product.getEnumProductCategory();
    }
}