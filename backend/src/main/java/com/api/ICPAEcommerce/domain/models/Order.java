package com.api.ICPAEcommerce.domain.models;

import com.api.ICPAEcommerce.domain.dtos.OrderDTO;
import com.api.ICPAEcommerce.domain.enums.EnumPaymenType;
import com.api.ICPAEcommerce.domain.enums.EnumOrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.List;

@Entity(name = "Order")
@Table (name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clientEmail;
    private OffsetDateTime orderDate;
    private Double orderPrice;
    private EnumPaymenType paymentType;

    @OneToMany
    private List<Product> items;

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private EnumOrderStatus status;

    public Order(OrderDTO orderDTO) {
        this.items.add(orderDTO.items());
        this.address = orderDTO.address();
        this.clientEmail = orderDTO.clientEmail();
        this.orderPrice = orderDTO.orderPrice();
        this.paymentType = orderDTO.enumPaymentType();
    }
}
