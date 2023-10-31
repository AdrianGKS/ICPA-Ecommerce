package com.api.ICPAEcommerce.domain.product;


public record ListProductDTO(
        Long id,

        String code,

        String name,

        String description,

        Double price,

        Integer quantity,

        EnumProductCategory enumProductCategory
) {
    public ListProductDTO(Product product) {
        this(product.getId(), product.getCode(), product.getName(), product.getDescription(), product.getPrice(), product.getQuantity(), product.getEnumProductCategory());
    }
}
