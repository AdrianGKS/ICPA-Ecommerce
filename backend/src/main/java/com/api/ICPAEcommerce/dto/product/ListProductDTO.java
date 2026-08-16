package com.api.ICPAEcommerce.dto.product;


import com.api.ICPAEcommerce.domain.product.EnumProductCategory;
import com.api.ICPAEcommerce.domain.product.Product;

import java.math.BigDecimal;

public record ListProductDTO(
        Long id,
        String code,
        String name,
        String description,
        BigDecimal price,
        Integer quantity,
        EnumProductCategory enumProductCategory
) {
    public ListProductDTO(Product product) {
        this(product.getId(), product.getCode(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity(), product.getEnumProductCategory());
    }
}
