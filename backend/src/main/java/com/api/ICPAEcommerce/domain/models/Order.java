package com.api.ICPAEcommerce.domain.models;

import com.api.ICPAEcommerce.domain.dtos.OrderDTO;
import com.api.ICPAEcommerce.domain.enums.EnumPaymenType;
import com.api.ICPAEcommerce.domain.enums.EnumOrderStatus;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;

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
//    @OneToMany(fetch = FetchType.EAGER)
//    @JoinColumn(name="produto_id", nullable=false)
    private String items;
    @Embedded
    private Address address;
    @Enumerated(EnumType.STRING)
    private EnumOrderStatus status = EnumOrderStatus.PENDING_PAYMENT;
    private String clientEmail;
    private OffsetDateTime orderDate;
    private Double oderPrice;
    private EnumPaymenType paymentType;

    public Order(OrderDTO orderDTO) {
        this.items = orderDTO.items();
//        this.address = "buscarNoBanco";
//        this.clientEmail = "buscarNoBanco";
//        this.orderPrice = "buscarNoBanco";
        this.paymentType = orderDTO.enumPaymentType();

    }
}
