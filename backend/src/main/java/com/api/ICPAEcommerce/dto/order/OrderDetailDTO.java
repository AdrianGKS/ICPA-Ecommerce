package com.api.ICPAEcommerce.dto.order;

import com.api.ICPAEcommerce.domain.address.Address;
import com.api.ICPAEcommerce.domain.order.EnumOrderStatus;
import com.api.ICPAEcommerce.domain.order.EnumPaymenType;
import com.api.ICPAEcommerce.dto.orderItem.OrderItemDetailDTO;

import java.time.OffsetDateTime;
import java.util.List;

public record OrderDetailDTO(
        Long id,
        String clientEmail,
        OffsetDateTime orderDate,
        Double orderPrice,
        EnumPaymenType paymentType,
        EnumOrderStatus status,
        Address address,
        List<OrderItemDetailDTO> items
) {}