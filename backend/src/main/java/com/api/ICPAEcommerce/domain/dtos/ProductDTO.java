package com.api.ICPAEcommerce.domain.dtos;

import com.api.ICPAEcommerce.domain.enums.EnumProductCategory;
import com.api.ICPAEcommerce.domain.models.Image;
import com.api.ICPAEcommerce.domain.models.Product;
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
    String title;
    @NotBlank
    String description;
    @Valid
    Set<ImageDTO> image;
    @DecimalMin("0.01")
    Double price;
    @Min(1)
    Integer quantity;
    @NotNull
    EnumProductCategory enumProductCategory;

    public ProductDTO() {}

    public ProductDTO(Product product) {
        this.code = product.getCode();
        this.title = product.getTitle();
        this.description = product.getDescription();
        this.image = new HashSet<>();

        for (Image image : product.getImages()) {
            this.image.add(new ImageDTO(image));
        }

        this.price = product.getPrice();
        this.quantity = product.getQuantity();
        this.enumProductCategory = product.getEnumProductCategory();
    }
}