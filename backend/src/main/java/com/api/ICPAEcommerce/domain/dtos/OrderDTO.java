package com.api.ICPAEcommerce.domain.dtos;

import com.api.ICPAEcommerce.domain.enums.EnumPaymenType;
import com.api.ICPAEcommerce.domain.enums.EnumOrderStatus;
import com.api.ICPAEcommerce.domain.models.Address;
import com.api.ICPAEcommerce.domain.models.Product;
import jakarta.validation.Valid;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;


import java.time.OffsetDateTime;
import java.util.List;

public record OrderDTO(

        Long id,
        @NotBlank
        Product items,
        @Valid
        Address address,
        EnumOrderStatus status,
        @Email
        String clientEmail,
        OffsetDateTime orderDate,
        @DecimalMin("0.01")
//        @NotNull
        Double orderPrice,
        @NotNull
        EnumPaymenType enumPaymentType
) {

}
