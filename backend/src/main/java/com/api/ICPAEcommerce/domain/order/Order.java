// Entidade Order
package com.api.ICPAEcommerce.domain.order;

import com.api.ICPAEcommerce.domain.address.Address;
import com.api.ICPAEcommerce.domain.orderItem.OrderItem;
import jakarta.persistence.*;
import lombok.*;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity(name = "Order")
@Table(name = "orders")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "orders_sequence_generator")
    @SequenceGenerator(name = "orders_sequence_generator", sequenceName = "orders_seq", allocationSize = 50)
    private Long id;

    private String clientEmail;
    private OffsetDateTime orderDate;

    // Opcional no futuro: Mudar para BigDecimal para evitar dízimas de ponto flutuante
    private Double orderPrice;

    @Enumerated(EnumType.STRING)
    private EnumPaymenType paymentType;

    // Relação alterada de Product para OrderItem
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<OrderItem> items = new ArrayList<>();

    @Embedded
    private Address address;

    @Enumerated(EnumType.STRING)
    private EnumOrderStatus status;
}