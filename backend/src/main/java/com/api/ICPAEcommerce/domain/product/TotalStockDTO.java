package com.api.ICPAEcommerce.domain.product;

import jakarta.validation.constraints.DecimalMin;

import java.math.BigDecimal;

public record TotalStockDTO(
        @DecimalMin("0.00")
        BigDecimal totalStock
) {
}
