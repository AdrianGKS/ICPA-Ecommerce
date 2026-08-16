package com.api.ICPAEcommerce.domain.orderItem;

import com.api.ICPAEcommerce.domain.order.Order;
import com.api.ICPAEcommerce.domain.product.Product;
import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Entity(name = "OrderItem")
@Table(name = "order_items")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class OrderItem {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "order_items_sequence_generator")
    @SequenceGenerator(name = "order_items_sequence_generator", sequenceName = "order_items_seq", allocationSize = 50)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id")
    private Product product;

    private Integer quantity;

    @Column(precision = 19, scale = 2)
    private BigDecimal priceAtTimeOfPurchase;
}