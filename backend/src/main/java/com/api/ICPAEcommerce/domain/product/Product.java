package com.api.ICPAEcommerce.domain.product;

import jakarta.persistence.*;
import lombok.*;
import java.math.BigDecimal;

@Table(name = "products")
@Entity(name = "Product")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "products_sequence_generator")
    @SequenceGenerator(name = "products_sequence_generator", sequenceName = "products_seq", allocationSize = 50)
    private Long id;

    private String code;
    private String name;
    private String description;

    @Column(precision = 19, scale = 2)
    private BigDecimal price;
    private Integer quantity;

    @Enumerated(EnumType.STRING)
    private EnumProductCategory enumProductCategory;

    private Boolean active;

    @Version
    private Long version;

    public void deactivate() {
        this.active = false;
    }
}