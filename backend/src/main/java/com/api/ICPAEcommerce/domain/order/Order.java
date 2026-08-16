package com.api.ICPAEcommerce.domain.order;

import com.api.ICPAEcommerce.domain.address.Address;
import com.api.ICPAEcommerce.domain.product.Product;
import com.api.ICPAEcommerce.dto.order.OrderDTO;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Entity(name = "Order")
@Table (name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    private String clientEmail;
    private OffsetDateTime orderDate;
    private Double orderPrice;
    private EnumPaymenType paymentType;

    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private List<Product> items = new ArrayList<>();

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private EnumOrderStatus status;

    public Order(OrderDTO orderDTO) {
        // Mapear itens do DTO para entidades Product e garantir relação bidirecional
        if (orderDTO.items() != null) {
            this.items = orderDTO.items().stream()
                    .map(pdto -> {
                        Product p = new Product(pdto);
                        p.setOrder(this);
                        return p;
                    })
                    .collect(Collectors.toList());
        } else {
            this.items = new ArrayList<>();
        }

        this.address = orderDTO.address();
        this.clientEmail = orderDTO.clientEmail();
        this.orderPrice = orderDTO.orderPrice();
        this.paymentType = orderDTO.enumPaymentType();
    }
}
