package com.api.ICPAEcommerce.dto.orderItem;

public record OrderItemDetailDTO(
        Long productId,
        String productName,
        Integer quantity,
        Double priceAtTimeOfPurchase
) {}