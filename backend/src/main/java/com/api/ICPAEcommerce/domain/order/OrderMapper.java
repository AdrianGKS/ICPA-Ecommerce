package com.api.ICPAEcommerce.domain.order;

import com.api.ICPAEcommerce.domain.orderItem.OrderItem;
import com.api.ICPAEcommerce.dto.order.OrderDetailDTO;
import com.api.ICPAEcommerce.dto.orderItem.OrderItemDetailDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDetailDTO toDetailDTO(Order entity);

    @Mapping(target = "productId", source = "product.id")
    @Mapping(target = "productName", source = "product.name")
    OrderItemDetailDTO toItemDetailDTO(OrderItem item);
}
