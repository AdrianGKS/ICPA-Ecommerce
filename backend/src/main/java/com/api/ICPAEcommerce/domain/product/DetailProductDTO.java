package com.api.ICPAEcommerce.domain.product;

import com.api.ICPAEcommerce.domain.file.UploadResquestResult;

public record DetailProductDTO (
        Long id,
        String code,
        String name,
        String description,
        double price,
        int quantity,
        EnumProductCategory enumProductCategory

){
    public DetailProductDTO(Product product) {
        this(product.getId(),
                product.getCode(),
                product.getName(),
                product.getDescription(),
                product.getPrice(),
                product.getQuantity(),
                product.getEnumProductCategory());
    }
}
