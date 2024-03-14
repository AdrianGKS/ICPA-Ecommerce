package com.api.ICPAEcommerce.domain.product;

public record UpdateProductDTO(
        String code,
        String name,
        String description,
        double price,
        int quantity
) {
}
