// DTOs de Entrada (Request)
package com.api.ICPAEcommerce.dto.order;

import com.api.ICPAEcommerce.domain.address.Address;
import com.api.ICPAEcommerce.domain.order.EnumPaymenType;
import com.api.ICPAEcommerce.dto.orderItem.OrderItemRequestDTO;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import java.util.List;

public record OrderRequestDTO(
        @NotEmpty
        List<OrderItemRequestDTO> items,

        @Valid
        @NotNull
        Address address,

        @Email
        @NotNull
        String clientEmail,

        @NotNull
        EnumPaymenType paymentType
) {}